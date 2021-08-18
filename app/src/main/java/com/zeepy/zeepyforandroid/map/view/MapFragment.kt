package com.zeepy.zeepyforandroid.map.view

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMapBinding
import com.zeepy.zeepyforandroid.map.data.Building
import com.zeepy.zeepyforandroid.map.viewmodel.MapViewModel
import com.zeepy.zeepyforandroid.util.WidthResizeAnimation
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    //private val ACCESS_FINE_LOCATION = 1000
    private lateinit var mapViewContainer: ViewGroup
    private lateinit var mapView: MapView
    private lateinit var resizeAnimation: WidthResizeAnimation
    private val mapViewModel: MapViewModel by activityViewModels()
    private var buildings = listOf<Building>()
    private var markers = mutableListOf<MapPOIItem>()
    private val eventListener = MarkerEventListener(context)


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {

        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = MapView(activity)
        mapViewContainer = binding.mapViewContainer
        mapViewContainer.addView(mapView)
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

        }
        //draw marker
        mapView.addPOIItem(marker)
    }



}