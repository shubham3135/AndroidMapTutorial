package com.shubhamkumarwinner.mapswithmarker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
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

        //setting the minimum and maximum zoom
//        map.setMinZoomPreference(16.0f)
//        map.setMaxZoomPreference(24.0f)
//
//        map.mapType = GoogleMap.MAP_TYPE_NORMAL
//        map.addMarker(
//            MarkerOptions()
//                .position(homeLatLng)
//                .title("Marker in Dumri")
//        )
//        map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng))
        map.isTrafficEnabled = true

        val dumriBounds = LatLngBounds(
            LatLng(23.0, 86.0),  // SW bounds
            LatLng(27.0, 87.0) // NE bounds
        )

        // for restricting user to scroll between given bounds
//        map.setLatLngBoundsForCameraTarget(dumriBounds)
//
//        map.addMarker(
//            MarkerOptions()
//                .position(dumriBounds.center)
//                .title("Marker in Dumri")
//        )
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dumriBounds.center, 10f))

//        CameraUpdateFactory.scrollBy(20f, 30f)


        // for moving camera from one place to another use the below code
        val sydney = LatLng(-33.88, 151.21)
        val mountainView = LatLng(37.4, -122.1)

        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )

// Move the camera instantly to Sydney with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

// Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomIn())

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        map.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        val cameraPosition = CameraPosition.Builder()
            .target(mountainView) // Sets the center of the map to Mountain View
            .zoom(17f)            // Sets the zoom
            .bearing(90f)         // Sets the orientation of the camera to east
            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map.addMarker(
            MarkerOptions()
                .position(mountainView)
                .title("Marker in MountainView")
        )
    }
}