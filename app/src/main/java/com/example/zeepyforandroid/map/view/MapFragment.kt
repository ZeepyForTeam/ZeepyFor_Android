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
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentMapBinding
import com.example.zeepyforandroid.map.data.Building
import com.example.zeepyforandroid.map.viewmodel.MapViewModel
import com.example.zeepyforandroid.util.WidthResizeAnimation
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

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
        
        // ?????? ????????? ?????????
        setMarker(37.505834449999995, 126.96320847343215, R.drawable.emoji_5_map)
        setMarker(37.505634469999995, 126.96320857343215, R.drawable.emoji_1_map)

//        if (checkLocationService()) {
//            permissionCheck()
//        } else {
//            Toast.makeText(activity, "GPS??? ????????????", Toast.LENGTH_SHORT).show()
//        }

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

    //?????? ??????
    private fun setToolbar() {
        binding.mapToolbar.run {
            setTitle("????????? ????????????")
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
            //TODO: marker info layout ?????????
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

    // ????????? ?????? ????????? (????????? ??????)
    private fun setMarker(lat: Double, lng: Double, resourceID: Int) {
        val marker = MapPOIItem()
        marker.apply {
            itemName = "????????? ??????"
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = resourceID
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
            //customSelectedImageResourceId = R.drawable.icon_map_act //TODO: ??? ????????? ???????????? ??? ?????? ????????? ??????
            isCustomImageAutoscale = true

        }
        //draw marker
        mapView.addPOIItem(marker)
    }


    // ?????? ?????? ??????
    private fun permissionCheck() {
        val preference = requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ????????? ?????? ??????
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // ?????? ?????? (?????? ??? ??? ?????????)
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("?????? ????????? ?????????????????? ?????? ????????? ??????????????????.")
                builder.setPositiveButton("??????") { dialog, which ->
                    requestMultiplePermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }
                builder.setNegativeButton("??????") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // ?????? ?????? ??????
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    requestMultiplePermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                } else {
                    // ?????? ?????? ?????? ?????? (??? ?????? ???????????? ??????)
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setMessage("?????? ????????? ?????????????????? ???????????? ?????? ????????? ??????????????????.")
                    builder.setPositiveButton("???????????? ??????") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity?.packageName))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("??????") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            // ????????? ?????? ??????
            startTracking()
        }
    }

    //?????? ?????? ??????
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

    // GPS??? ??????????????? ??????
    private fun checkLocationService(): Boolean {
        val locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // ???????????? ??????
    private fun startTracking() {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }



}