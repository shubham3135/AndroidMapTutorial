package com.shubhamkumarwinner.mapswithmarker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerDragListener, OnInfoWindowClickListener {
    lateinit var map: GoogleMap

    var latitude = 23.99497
    var longitude = 86.01111
    val homeLatLng = LatLng(latitude, longitude)
    var markerDumri: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)

        // for disabling click events
//        mapFragment?.view?.isClickable = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val zoomLevel = 18f
        markerDumri = map.addMarker(
            MarkerOptions()
                .position(homeLatLng)
                .title("Marker in Dumri")
                .snippet("Latitude: $latitude Longitude: $longitude")
                .draggable(true)
        )

        // for enabling zoom controller in map
        map.uiSettings.isZoomControlsEnabled = true

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        markerDumri!!.tag = 0
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.isTrafficEnabled = true

        //adding marker drag listener
        map.setOnMarkerDragListener(this)

        //adding info window clickListener
        map.setOnInfoWindowClickListener(this)
    }

    override fun onMarkerDragStart(marker: Marker) {
        val position = marker.position
        Log.d("Drag", "${position.latitude}  ${position.longitude}")
    }

    override fun onMarkerDrag(marker: Marker) {
//        val position = marker.position
//        Log.d("Drag", "${position.latitude}  ${position.longitude}")
    }

    override fun onMarkerDragEnd(marker: Marker) {
        val position = marker.position
        latitude = position.latitude
        longitude = position.longitude
        markerDumri!!.snippet = "Latitude: $latitude Longitude: $longitude"
        Log.d("Drag", "${position.latitude}  ${position.longitude}")
    }

    override fun onInfoWindowClick(marker: Marker) {
        val position = marker.position
        Toast.makeText(
            this, "${position.latitude}  ${position.longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }
}


