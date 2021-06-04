package com.shubhamkumarwinner.mapswithmarker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private val homeIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.home)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_home, color)
    }

    lateinit var map: GoogleMap


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
        val latitude = 23.99497
        val longitude = 86.01111
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 18f

        // for enabling zoom controller in map
        map.uiSettings.isZoomControlsEnabled = true

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        val markerDumri = map.addMarker(
            MarkerOptions()
                .position(homeLatLng)
                .title("My home")
                    //for making marker draggable
                .draggable(true)
                    // for customizing marker color
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    // for marker image/icon
                .icon(homeIcon)
                    // for flattening marker
//                .flat(true)
                    //for rotating marker
//                .rotation(90f)
        )
        markerDumri.tag = 0
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.isTrafficEnabled = true

        map.setOnMarkerClickListener {marker ->
            val clickCount = marker.tag as? Int
            clickCount?.let {
                val newClickCount = it + 1
                marker.tag = newClickCount
                Toast.makeText(this, "${marker.title} clicked $newClickCount times", Toast.LENGTH_SHORT).show()
            }
            return@setOnMarkerClickListener false
        }

        //adding marker drag listener
        map.setOnMarkerDragListener(this)
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
        Log.d("Drag", "${position.latitude}  ${position.longitude}")
    }
}


