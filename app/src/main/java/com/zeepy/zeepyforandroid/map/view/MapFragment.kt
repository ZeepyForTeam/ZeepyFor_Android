package com.zeepy.zeepyforandroid.map.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMapBinding
import com.zeepy.zeepyforandroid.map.data.Building
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.usecase.util.Result
import com.zeepy.zeepyforandroid.map.viewmodel.MapViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.MapHelper
import com.zeepy.zeepyforandroid.util.MetricsConverter
import com.zeepy.zeepyforandroid.util.WidthResizeAnimation
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.n.api.internal.NativeDeviceCheckUtilsMapLibrary
import net.daum.mf.map.n.api.internal.NativeMapController
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var mapViewContainer: ViewGroup
    private lateinit var mapView: MapView
    private lateinit var lastSelectedMarker: MapPOIItem
    private lateinit var myLocationButton: ConstraintLayout
    private lateinit var mapHelper: MapHelper
    private val permReqLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                startTrackingLocation()
            } else {
                Toast.makeText(context, "위치 권한이 거절되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    private lateinit var resizeAnimation: WidthResizeAnimation
    private val viewModel: MapViewModel by viewModels<MapViewModel>()
    private var buildings = listOf<BuildingModel>()
    private var markers = mutableListOf<MapPOIItem>()
    private var markerId = 0
    private var lastSelectedMarkerOriginalImage: Int = -1
    private var existSelectedMarker = false
    private val markerEventListener = MarkerEventListener(context)
    private val mapViewEventListener = MapViewEventListener(context)
    private var currentZoomLevel = 2

    companion object {
        val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {

        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // 동적으로 지도뷰 & 지도 위 버튼들 추가
        mapView = MapView(activity)
        mapViewContainer = binding.mapViewContainer
        myLocationButton = this.layoutInflater.inflate(R.layout.view_my_location_finder, mapViewContainer, false) as ConstraintLayout
        mapViewContainer.addView(mapView)
        mapViewContainer.addView(myLocationButton)
        (myLocationButton.layoutParams as RelativeLayout.LayoutParams).apply {
            marginStart = MetricsConverter.dpToPixel(16F, context).toInt()
            topMargin = MetricsConverter.dpToPixel(16F, context).toInt()
        }

        // 현재위치 버튼 클릭 시 permission 요청 (TODO: 요청 시점 수정될 수도)
        myLocationButton.setOnClickListener { getGpsLocation() }

        // setMarkersObserver()
        setOptionButton()
        setToolbar()
        mapView.setPOIItemEventListener(markerEventListener)
        mapView.setMapViewEventListener(mapViewEventListener)
        // 마커 띄우기 테스트
        setMarker(37.505834449999995, 126.96320847343215, R.drawable.emoji_5_map)
        setMarker(37.505634469999995, 126.96320857343215, R.drawable.emoji_1_map)

        Log.e("access token", "${userPreferenceManager.fetchUserAccessToken()}")
        // FIXME: Error accessing mapPointBounds
        // FIXME: 현재 카카오 지도 네이티브 소스에서 Fatal signal 11(SIGSEGV) 잘못된 메모리 참조 에러 발생 (getZoomLevel이나 getMapPointBounds를 불러올 수 없는 상황)
        Log.e("mapCenterPoint", "" + mapView.mapCenterPoint.mapPointGeoCoord.latitude + "," + mapView.mapCenterPoint.mapPointGeoCoord.longitude)
        Log.e("mapZoomLevel", "" + currentZoomLevel)
        mapHelper = MapHelper(requireActivity(), mapView, currentZoomLevel)
        mapHelper.setTopLeftPoint()
        mapHelper.setBottomRightPoint()
        mapHelper.setRemainingPoints()
        Log.e("topLeftPoint", "" + mapHelper.topLeftLat + "," + mapHelper.topLeftLng)
        Log.e("bottomRightPoint", "" + mapHelper.bottomRightLat + "," + mapHelper.bottomRightLng)

        viewModel.getBuildingsByLocation(37.961350, 35.161614, 129.483989, 126.285148)


        viewModel.fetchBuildingsResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    result.data.let {
                        this.buildings = it
                        setMarkersList()
                        mapView.setPOIItemEventListener(markerEventListener)
                    }
                }
                is Result.Error -> {
                    if (result.exception.message == "401") {
                        Log.e("Unauthorized User", result.toString())
                    }
                }
            }
        })
    }

    /**
     * Render markers
     */
    private fun setMarkersList() {
        buildings.indices.forEach { index ->
            markers.add(
                MapPOIItem().apply {
                    mapPoint = MapPoint.mapPointWithGeoCoord(buildings[index].latitude, buildings[index].longitude)
                }
            )
            mapView.addPOIItems(markers.toTypedArray())
        }
    }

    private fun setOptionButton() {
        binding.optionBtnLayout.optionBtn.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(it, 800, false)
            resizeAnimation.duration = 600
            Log.d("original width", resizeAnimation.originalWidth.toString())
            Log.d("target width", resizeAnimation.targetWidth.toString())
            it.startAnimation(resizeAnimation)
            it.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    binding.optionBtnLayout.optionOne.visibility = View.VISIBLE
                    binding.optionBtnLayout.optionTwo.visibility = View.VISIBLE
                    binding.optionBtnLayout.optionThree.visibility = View.VISIBLE
                    binding.optionBtnLayout.optionFour.visibility = View.VISIBLE
                    binding.optionBtnLayout.optionFive.visibility = View.VISIBLE
                }
            },300)
        }
        binding.optionBtnLayout.optionBtn.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(it, 218, false)
            resizeAnimation.duration = 600
            Log.d("original width", resizeAnimation.originalWidth.toString())
            Log.d("target width", resizeAnimation.targetWidth.toString())
            it.startAnimation(resizeAnimation)
            binding.optionBtnLayout.optionBtn.visibility = View.VISIBLE
            binding.optionBtnLayout.optionOne.visibility = View.GONE
            binding.optionBtnLayout.optionTwo.visibility = View.GONE
            binding.optionBtnLayout.optionThree.visibility = View.GONE
            binding.optionBtnLayout.optionFour.visibility = View.GONE
            binding.optionBtnLayout.optionFive.visibility = View.GONE
        }
    }

    private fun setToolbar() {
        binding.mapToolbar.run {
            setTitle("지도로 검색하기")
            setBackButton {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    inner class MapViewEventListener(val context: Context?): MapView.MapViewEventListener {
        override fun onMapViewInitialized(p0: MapView?) {
        }

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
            currentZoomLevel = p1
        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        }

    }


    inner class MarkerEventListener(val context: Context?): MapView.POIItemEventListener {
        // Set marker detail visibility
        private fun setMarkerDetailVisibility(index: Int) {
            viewModel.setMarkerClick(buildings[index].id)
        }

        private fun setMarkerDetailAnimation() {
            if (binding.layoutMarkerDetail.visibility != View.VISIBLE) {
                binding.layoutMarkerDetail.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.map_marker_detail_vertical_translation)
                )
            }
        }

        /**
         * 마커가 선택되었을 시 실행되는 함수
         */
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            if (!existSelectedMarker) {
                lastSelectedMarker = p1!!.clone()
                lastSelectedMarkerOriginalImage = p1.customImageResourceId
                p0?.removePOIItem(p1)

                lastSelectedMarker.setCustomImageAnchor(0.5F, 1F)
                lastSelectedMarker.customImageResourceId = R.drawable.icon_map_act

                p0?.addPOIItem(lastSelectedMarker)
                mapView.setMapCenterPoint(lastSelectedMarker.mapPoint, false)
                existSelectedMarker = true
            } else {
                if (p1?.tag != lastSelectedMarker.tag) {
                    val markerUnselected = lastSelectedMarker.clone()
                    p0?.removePOIItem(lastSelectedMarker)

                    markerUnselected.setCustomImageAnchor(0.5F, 0.5F)
                    markerUnselected.customImageResourceId = lastSelectedMarkerOriginalImage

                    p0?.addPOIItem(markerUnselected)

                    lastSelectedMarker = p1!!.clone()
                    lastSelectedMarkerOriginalImage = p1.customImageResourceId
                    p0?.removePOIItem(p1)

                    lastSelectedMarker.setCustomImageAnchor(0.5F, 1F)
                    lastSelectedMarker.customImageResourceId = R.drawable.icon_map_act

                    p0?.addPOIItem(lastSelectedMarker)
                    mapView.setMapCenterPoint(lastSelectedMarker.mapPoint, false)
                    existSelectedMarker = true
                }
            }

        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        }

    }

    private fun setSelectedMarkerOverlay(index: Int) {

    }

    /**
     * 마커를 생성하고 지도에 띄워주는 함수
     */
    private fun setMarker(lat: Double, lng: Double, resourceID: Int) {
        val marker = MapPOIItem()
        marker.apply {
            itemName = "테스트 마커"
            tag = markerId++
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
            setCustomImageAnchor(0.5F, 0.5F)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = resourceID
            // customSelectedImageResourceId 사용했을 때 위치 오차가 발생 (이미지 크기 차이로 인해 발생하는 것으로 추정)
            isCustomImageAutoscale = false
            isShowCalloutBalloonOnTouch = false
        }
        // draw marker
        mapView.addPOIItem(marker)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lng), false)
    }

    /**
     * 마커 객체를 복사하는 함수
     */
    private fun MapPOIItem.clone(): MapPOIItem {
        val marker = MapPOIItem()
        marker.itemName = this.itemName
        marker.tag = this.tag
        marker.mapPoint = this.mapPoint
        marker.markerType = this.markerType
        marker.customImageResourceId = this.customImageResourceId
        marker.isCustomImageAutoscale = this.isCustomImageAutoscale
        marker.isShowCalloutBalloonOnTouch = this.isShowCalloutBalloonOnTouch

        return marker
    }

    private fun getGpsLocation() {
        activity?.let {
            if (hasPermissions(activity as Context, LOCATION_PERMISSIONS)) {
                startTrackingLocation()
            } else {
                permReqLauncher.launch(LOCATION_PERMISSIONS)
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startTrackingLocation() {
        this.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }



}