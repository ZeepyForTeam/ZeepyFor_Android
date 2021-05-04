package com.example.zeepyforandroid.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentMapBinding
import com.example.zeepyforandroid.map.data.searchdialog.SearchDialog
import com.example.zeepyforandroid.map.viewmodel.MapViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {

    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private lateinit var locationSource: FusedLocationSource

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private val mapViewModel: MapViewModel by viewModel { parametersOf() }

    // 여러 개의 마커 선언 및 초기화
    private var marker0 = Marker()
    private var marker1 = Marker()
    private var marker2 = Marker()
    private var marker3 = Marker()
    private var marker4 = Marker()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        setToolbar()
        mapView = view.findViewById(R.id.mv_NMap)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this) // Calls onMapReady

        mapView.run {
            onCreate(savedInstanceState)
            getMapAsync { navermap ->
                naverMap = navermap
                naverMap.run {
                    this.locationSource = locationSource

                    setOnMapClickListener { pointF, latLng ->
                        mapView.parent.requestDisallowInterceptTouchEvent(true)
                        //resetSearchView()
                    }
                }

                //setMyLocation()
            }
        }

        mapViewModel.searchAddressData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { geocodingResponse ->
                val searchDialogs = mutableListOf<SearchDialog>()

                geocodingResponse.addresses?.forEach {
                    it?.roadAddress?.let { title ->
                        searchDialogs.add(SearchDialog(title))
                    }
                }
            }
        )

    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        setMarker(marker0, 37.5632424, 126.9834535, R.drawable.emoji_5_map)
        setMarker(marker1, 37.5632500, 126.9836324, R.drawable.emoji_1_map)

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun setToolbar() {
        binding.mapToolbar.run {
            setTitle("지도로 검색하기")
            setBackButton {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun setMyLocation() {
        //showLoading()

        locationSource.activate {
            val myLocation = LatLng(it?.latitude!!, it.longitude)

            //CONTINUE...
        }
    }

    private fun setMarker(marker: Marker, lat: Double, lng: Double, resourceID: Int) {
        // marker.isIconPerspectiveEnabled = true
        marker.icon = OverlayImage.fromResource(resourceID)
        // marker.alpha = 0.8f
        marker.position = LatLng(lat, lng)
        marker.map = naverMap
    }


}