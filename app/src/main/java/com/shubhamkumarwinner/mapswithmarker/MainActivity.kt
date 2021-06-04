package com.shubhamkumarwinner.mapswithmarker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latitude = 23.99497
        val longitude = 86.01111
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 18f

        // for enabling zoom controller in map
        map.uiSettings.isZoomControlsEnabled = true

        // for disabling compass in map
//        map.uiSettings.isCompassEnabled = false

        // for enabling level picker
//        map.uiSettings.isIndoorLevelPickerEnabled = true

        // for enabling map toolbar in right bottom
        map.uiSettings.isMapToolbarEnabled = true

        // for disabling zoom gestures
//        map.uiSettings.isZoomGesturesEnabled = false

        // for disabling scroll gestures
//        map.uiSettings.isScrollGesturesEnabled = false

        // for disabling tilt gestures
//        map.uiSettings.isTiltGesturesEnabled = false

        // for disabling rotate gestures
        map.uiSettings.isRotateGesturesEnabled = false

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.addMarker(
            MarkerOptions()
                .position(homeLatLng)
                .title("Marker in Dumri")
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.isTrafficEnabled = true

        val dumriBounds = LatLngBounds(
            LatLng(23.0, 85.0),  // SW bounds
            LatLng(27.0, 87.0) // NE bounds
        )

        // for restricting user to scroll between given bounds
        map.setLatLngBoundsForCameraTarget(dumriBounds)


    }
}