package com.zeepy.zeepyforandroid.map.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMapBinding
import com.zeepy.zeepyforandroid.map.data.Building
import com.zeepy.zeepyforandroid.map.viewmodel.MapViewModel
import com.zeepy.zeepyforandroid.util.MetricsConverter
import com.zeepy.zeepyforandroid.util.WidthResizeAnimation
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    private lateinit var mapViewContainer: ViewGroup
    private lateinit var mapView: MapView
    private lateinit var myLocationButton: ConstraintLayout
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
    private val mapViewModel: MapViewModel by activityViewModels()
    private var buildings = listOf<Building>()
    private var markers = mutableListOf<MapPOIItem>()
    private val eventListener = MarkerEventListener(context)

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

        myLocationButton.setOnClickListener { getGpsLocation() }
        setMarkersObserver()
        setOptionButton()
        setToolbar()
        
        // 마커 띄우기 테스트
        setMarker(37.505834449999995, 126.96320847343215, R.drawable.emoji_5_map)
        setMarker(37.505634469999995, 126.96320857343215, R.drawable.emoji_1_map)


    }

    private fun setMarkersObserver() {
        mapViewModel.markers.observe(viewLifecycleOwner) { markers ->
            markers?.let {
                this.buildings = markers
                setMarkersList()
                mapView.setPOIItemEventListener(eventListener)
            }
        }
    }

    private fun setOptionButton() {
        binding.optionBtnLayout.optionBtn.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(it, 800, false)
            resizeAnimation.duration = 600
            Log.d("original widthhhhhhhh", resizeAnimation.originalWidth.toString())
            Log.d("target widthhhhhhhh", resizeAnimation.targetWidth.toString())
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
            Log.d("original widthhhhhhhh", resizeAnimation.originalWidth.toString())
            Log.d("target widthhhhhhhh", resizeAnimation.targetWidth.toString())
            it.startAnimation(resizeAnimation)
            binding.optionBtnLayout.optionBtn.visibility = View.VISIBLE
            binding.optionBtnLayout.optionOne.visibility = View.GONE
            binding.optionBtnLayout.optionTwo.visibility = View.GONE
            binding.optionBtnLayout.optionThree.visibility = View.GONE
            binding.optionBtnLayout.optionFour.visibility = View.GONE
            binding.optionBtnLayout.optionFive.visibility = View.GONE
        }
    }

    //툴바 세팅
    private fun setToolbar() {
        binding.mapToolbar.run {
            setTitle("지도로 검색하기")
            setBackButton {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }



    //Add & Set markers from buildings
    private fun setMarkersList() {
        buildings.indices.forEach { index ->
            markers.add(
                MapPOIItem().apply {
                    mapPoint = MapPoint.mapPointWithGeoCoord(buildings[index].latitude, buildings[index].longitude)
                }
            )
        }
    }

    inner class MarkerEventListener(val context: Context?): MapView.POIItemEventListener {
        // Set marker detail visibility
        private fun setMarkerDetailVisibility(index: Int) {
            mapViewModel.setMarkerClick(buildings[index].id)
        }

        private fun setMarkerDetailAnimation() {
            if (binding.layoutMarkerDetail.visibility != View.VISIBLE) {
                binding.layoutMarkerDetail.startAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.map_marker_detail_vertical_translation)
                )
            }
        }

        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            //TODO: marker info layout 띄우기
            p1?.setCustomImageAnchor(0.5F, 1F)
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

    // 지도에 마커 띄우기 (테스트 용도)
    private fun setMarker(lat: Double, lng: Double, resourceID: Int) {
        val marker = MapPOIItem()
        marker.apply {
            itemName = "테스트 마커"
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = resourceID
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
            customSelectedImageResourceId = R.drawable.icon_map_act //FIXME: 이 이미지 사용했을 때 위치 오차가 발생
            isCustomImageAutoscale = true
            setCustomImageAnchor(0.5F, 0.5F)

        }
        //draw marker
        mapView.addPOIItem(marker)
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