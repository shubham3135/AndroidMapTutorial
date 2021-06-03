package com.shubhamkumarwinner.mapswithmarker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To add map dynamically add below line and remove name from xml file
        /*val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map, mapFragment)
            .commit()*/

        //To add map statically add name in xml and add below line
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

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.addMarker(
            MarkerOptions()
                .position(homeLatLng)
                .title("Marker in Dumri")
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.isTrafficEnabled = true
    }
}