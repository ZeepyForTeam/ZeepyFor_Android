package com.example.zeepyforandroid.map.view

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentMapBinding
import com.example.zeepyforandroid.map.data.Building
import com.example.zeepyforandroid.util.WidthResizeAnimation
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment : BaseFragment<FragmentMapBinding>() {

    //private val ACCESS_FINE_LOCATION = 1000
    private lateinit var mapViewContainer: ViewGroup
    private lateinit var mapView: MapView
    private lateinit var resizeAnimation: WidthResizeAnimation
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
        mapView.setPOIItemEventListener(eventListener)


        setOptionButton()
        setToolbar()
        
        // 마커 띄우기 테스트
        setMarker(37.505834449999995, 126.96320847343215, R.drawable.emoji_5_map)
        setMarker(37.505634469999995, 126.96320857343215, R.drawable.emoji_1_map)

//        if (checkLocationService()) {
//            permissionCheck()
//        } else {
//            Toast.makeText(activity, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun setOptionButton() {
        binding.optionBtn.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(binding.optionBtnLayout, 800, false)
            resizeAnimation.duration = 600
            Log.d("original widthhhhhhhh", resizeAnimation.originalWidth.toString())
            Log.d("target widthhhhhhhh", resizeAnimation.targetWidth.toString())
            binding.optionBtnLayout.startAnimation(resizeAnimation)
            binding.optionBtn.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    binding.optionOne.visibility = View.VISIBLE
                    binding.optionTwo.visibility = View.VISIBLE
                    binding.optionThree.visibility = View.VISIBLE
                    binding.optionFour.visibility = View.VISIBLE
                    binding.optionFive.visibility = View.VISIBLE
                }
            },300)


        }
        binding.optionBtnLayout.setOnClickListener {
            resizeAnimation = WidthResizeAnimation(it, 218, false)
            resizeAnimation.duration = 600
            Log.d("original widthhhhhhhh", resizeAnimation.originalWidth.toString())
            Log.d("target widthhhhhhhh", resizeAnimation.targetWidth.toString())
            it.startAnimation(resizeAnimation)
            binding.optionBtn.visibility = View.VISIBLE
            binding.optionOne.visibility = View.GONE
            binding.optionTwo.visibility = View.GONE
            binding.optionThree.visibility = View.GONE
            binding.optionFour.visibility = View.GONE
            binding.optionFive.visibility = View.GONE
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

    class MarkerEventListener(val context: Context?): MapView.POIItemEventListener {
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            //TODO: BottomSheet 띄우기
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
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
            //customSelectedImageResourceId = R.drawable.icon_map_act //TODO: 이 이미지 사용했을 때 위치 오차가 발생
            isCustomImageAutoscale = true

        }
        //draw marker
        mapView.addPOIItem(marker)
    }


    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 상태
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    requestMultiplePermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    requestMultiplePermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity?.packageName))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            // 권한이 있는 상태
            startTracking()
        }
    }

    //위치 권환 요청
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d(TAG, "${it.key} = ${it.value}")
            }
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                Log.d(TAG, "Permission granted")
                startTracking()
            } else {
                Log.d(TAG, "Permission not granted")
                permissionCheck()
            }
        }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위치추적 시작
    private fun startTracking() {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }



}