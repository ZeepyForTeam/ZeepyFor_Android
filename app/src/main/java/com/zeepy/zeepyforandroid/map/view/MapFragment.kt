package com.zeepy.zeepyforandroid.map.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMapBinding
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
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var mapViewContainer: ViewGroup
    private lateinit var mapView: MapView
    private var lastSelectedMarker: MapPOIItem = MapPOIItem()
    private lateinit var myLocationButton: ConstraintLayout
    private lateinit var mapHelper: MapHelper
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
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
    private var lastSelectedMarkerOriginalImage: Int = -1
    private var existSelectedMarker = false
    private val markerEventListener = MarkerEventListener(context)
    private val mapViewEventListener = MapViewEventListener(context)
    private var currentZoomLevel = 2
    private var currentMapCenterPoint: MapPoint? = null
    private var mapDisplayOffset: Float = 0F
    private val firstTimeBeingAddedMarkerMap: HashMap<Int, Boolean> = HashMap()


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
        binding.bottomSheetMap.viewModel = viewModel

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
        // FIXME: 아마 처음 지도 실행 시 현재위치 기준으로 mapCenterPoint를 가져와야 할 것 같음
        myLocationButton.setOnClickListener { getGpsLocation() }

        setOptionButton()
        setToolbar()
        mapView.setPOIItemEventListener(markerEventListener)
        mapView.setMapViewEventListener(mapViewEventListener)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetMap.root)
        bottomSheetBehavior.apply {
            this.setPeekHeight(200, true)
            this.isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
            this.skipCollapsed = true
        }


        Log.e("access token", "${userPreferenceManager.fetchUserAccessToken()}")

        // FIXME: 현재 카카오 지도 네이티브 소스에서 Fatal signal 11(SIGSEGV) 잘못된 메모리 참조 에러 발생 (getZoomLevel이나 getMapPointBounds를 불러올 수 없는 상황)

        // FIXME: Make this an observer function
        viewModel.fetchBuildingsResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    result.data.let {
                        this.buildings = it
                        setMarkersList()
                    }
                }
                is Result.Error -> {
                    if (result.exception.message == "401") {
                        Log.e("Unauthorized User", result.toString())
                    }
                }
            }
        })
        viewModel.buildingSelectedId.observe(viewLifecycleOwner, { id ->
            // show bottomSheet Dialog
            if (id != -1) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

        })
    }

    /**
     * Render markers
     */
    private fun setMarkersList() {
        buildings.indices.forEach { index ->
            if (buildings[index].reviews.isNotEmpty()) {
                Log.e("markerMap", firstTimeBeingAddedMarkerMap[buildings[index].id].toString())

                if (firstTimeBeingAddedMarkerMap[buildings[index].id] == null) {
                    markers.add(
                        MapPOIItem().apply {
                            mapPoint = MapPoint.mapPointWithGeoCoord(buildings[index].latitude, buildings[index].longitude)
                            itemName = "건물"
                            tag = buildings[index].id
                            setCustomImageAnchor(0.5F, 0.5F)
                            markerType = MapPOIItem.MarkerType.CustomImage

                            // 소통 성향별로 마커 이미지 설정
                            // TODO: 대표 소통성향을 어떤 로직으로 설정해야할지? 리뷰 중에 가장 많은 소통성향? 일단은 첫번째 리뷰 기준으로 설정함
                            // TODO: 나머지 성향 조건 추가
                            when (buildings[index].reviews[0].communcationTendency) {
                                "SOFTY" -> {
                                    customImageResourceId = R.drawable.emoji_2_map
                                }
                                "GRAZE" -> {
                                    customImageResourceId = R.drawable.emoji_3_map
                                }
                            }
                            isCustomImageAutoscale = false
                            isShowCalloutBalloonOnTouch = false
                        }
                    )
                    firstTimeBeingAddedMarkerMap[buildings[index].id] = true
                }
            }
        }
        markers.forEach {
            if (firstTimeBeingAddedMarkerMap[it.tag] == true) {
                mapView.addPOIItem(it)
            }
            firstTimeBeingAddedMarkerMap[it.tag] = false
        }
    }

    private fun setOptionButton() {
        val metrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(metrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            (display?.getMetrics(metrics))
        }

        val displayWidth = metrics.widthPixels
        val targetWidth = (displayWidth - MetricsConverter.dpToPixel(32F, context)).roundToInt()

        binding.optionBtn.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(binding.optionBtnLayout, targetWidth, false)
            resizeAnimation.duration = 600
            //Log.d("original width", resizeAnimation.originalWidth.toString())
            //Log.d("target width", resizeAnimation.targetWidth.toString())
            binding.optionBtnLayout.startAnimation(resizeAnimation)
            it.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.optionOne.visibility = View.VISIBLE
                binding.optionTwo.visibility = View.VISIBLE
                binding.optionThree.visibility = View.VISIBLE
                binding.optionFour.visibility = View.VISIBLE
                binding.optionFive.visibility = View.VISIBLE
            },300)
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
            currentMapCenterPoint = p1
        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
            currentZoomLevel = p1
            if (currentZoomLevel < 3) {
                mapDisplayOffset = binding.mapToolbar.height + binding.edittextSearchMap.height + MetricsConverter.dpToPixel(16F, context)
                mapHelper = MapHelper(requireActivity(), currentZoomLevel, currentMapCenterPoint!!, mapDisplayOffset)
                mapHelper.setMapMetrics()
                mapHelper.setFourPoints()

                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getBuildingsByLocation(mapHelper.bottomRightLat, mapHelper.topLeftLat, mapHelper.topLeftLng, mapHelper.bottomRightLng)
                }, 1500)
            }
        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if (lastSelectedMarker.tag != 0) {
                //Unselect marker
                val markerUnselected = lastSelectedMarker.clone()
                p0?.removePOIItem(lastSelectedMarker)

                markerUnselected.setCustomImageAnchor(0.5F, 0.5F)
                markerUnselected.customImageResourceId = lastSelectedMarkerOriginalImage

                p0?.addPOIItem(markerUnselected)
                existSelectedMarker = false
            }

            if (binding.optionBtn.visibility == View.GONE) {
                resizeAnimation = WidthResizeAnimation(binding.optionBtnLayout, 218, false)
                resizeAnimation.duration = 600
                //Log.d("original width", resizeAnimation.originalWidth.toString())
                //Log.d("target width", resizeAnimation.targetWidth.toString())
                binding.optionBtnLayout.startAnimation(resizeAnimation)
                binding.optionBtn.visibility = View.VISIBLE
                binding.optionOne.visibility = View.GONE
                binding.optionTwo.visibility = View.GONE
                binding.optionThree.visibility = View.GONE
                binding.optionFour.visibility = View.GONE
                binding.optionFive.visibility = View.GONE
            }

        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
            if (currentZoomLevel < 3) {
                mapDisplayOffset = binding.mapToolbar.height + binding.edittextSearchMap.height + MetricsConverter.dpToPixel(16F, context)
                //Log.e("zoomLevel", "" + currentZoomLevel)
                //Log.e("mapCenterPoint.mapPointGeoCoord", "" + currentMapCenterPoint?.mapPointGeoCoord?.latitude + "," + currentMapCenterPoint?.mapPointGeoCoord?.longitude)
                mapHelper = MapHelper(requireActivity(), currentZoomLevel, currentMapCenterPoint!!, mapDisplayOffset)
                mapHelper.setMapMetrics()
                //Log.e("toolBarHeight", "" + binding.mapToolbar.height)
                //Log.e("searchBarHeight", "" + binding.edittextSearchMap.height)
                //Log.e("offSet", "" + mapDisplayOffset)
                //Log.e("displayWidth", "" + mapHelper.displayWidth)
                //Log.e("displayHeight", "" + mapHelper.displayHeight)
                mapHelper.setFourPoints()
                //Log.e("TopLeft", "" + mapHelper.topLeftLat + "," + mapHelper.topLeftLng)
                //Log.e("TopRight", "" + mapHelper.topRightLat + "," + mapHelper.topRightLng)
                //Log.e("BottomLeft", "" + mapHelper.bottomLeftLat + "," + mapHelper.bottomLeftLng)
                //Log.e("BottomRight", "" + mapHelper.bottomRightLat + "," + mapHelper.bottomRightLng)

                viewModel.getBuildingsByLocation(mapHelper.bottomRightLat, mapHelper.topLeftLat, mapHelper.topLeftLng, mapHelper.bottomRightLng)
            }

        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        }

    }


    inner class MarkerEventListener(val context: Context?): MapView.POIItemEventListener {
        /**
         * 마커가 선택되었을 시 실행되는 함수
         */
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {

            // 마커 선택시 이미지 변경을 하기 위한 workaround
            if (!existSelectedMarker) {
                lastSelectedMarker = p1!!.clone()
                lastSelectedMarkerOriginalImage = p1.customImageResourceId
                p0?.removePOIItem(p1)

                lastSelectedMarker.setCustomImageAnchor(0.5F, 1F)
                lastSelectedMarker.customImageResourceId = R.drawable.icon_map_act
                viewModel.updateBuildingSelectedId(p1.tag)

                // Consider improving search performance
                buildings.indices.forEach { index ->
                    if (buildings[index].id == p1.tag) {
                        viewModel.updateBuildingSelected(buildings[index])
                    }
                }

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
                    viewModel.updateBuildingSelectedId(p1.tag)

                    // Consider improving search performance
                    buildings.indices.forEach { index ->
                        if (buildings[index].id == p1.tag) {
                            viewModel.updateBuildingSelected(buildings[index])
                        }
                    }

                    p0?.addPOIItem(lastSelectedMarker)
                    mapView.setMapCenterPoint(lastSelectedMarker.mapPoint, false)
                    existSelectedMarker = true
                } else {
                    // show bottomSheet again
                    viewModel.updateBuildingSelectedId(p1.tag)
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