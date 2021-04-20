package com.example.zeepyforandroid.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.example.zeepyforandroid.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var naverMap : NaverMap

    // 여러 개의 마커 선언 및 초기화
    private var marker0 = Marker()
    private var marker1 = Marker()
    private var marker2 = Marker()
    private var marker3 = Marker()
    private var marker4 = Marker()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mv_NMap)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this) // Calls onMapReady


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

    fun setMarker(marker: Marker, lat: Double, lng: Double, resourceID: Int) {
        // marker.isIconPerspectiveEnabled = true
        marker.icon = OverlayImage.fromResource(resourceID)
        // marker.alpha = 0.8f
        marker.position = LatLng(lat, lng)
        marker.map = naverMap
    }

}