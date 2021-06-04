package com.shubhamkumarwinner.mapswithmarker

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.model.*

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

        //for stroke pattern
        val pattern = listOf(
            Dot(), Gap(20F), Dash(30F), Gap(20F)
        )

        // add polyline in map
        // Instantiates a new Polyline object and adds points to define a rectangle
        val polylineOptions = PolylineOptions()
            .add(LatLng(23.89, 86.0))
            .add(LatLng(23.99, 86.0)) // North of the previous point, but at the same longitude
            .add(LatLng(23.99, 86.2)) // Same latitude, and 30km to the west
            .add(LatLng(23.89, 86.2)) // Same longitude, and 16km to the south
//            .add(LatLng(23.89, 86.0)) // Closes the polyline.
            .color(Color.RED)
            .startCap(RoundCap())
            .endCap(CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone), 16F))

// Get back the mutable Polyline
        val polyline = map.addPolyline(polylineOptions)
        polyline.isClickable = true
        //adding clicklistener in polyline
        map.setOnPolylineClickListener {
            val points = it.points
            Log.d("Drag", "$points")
        }

        // add polygon (triangle here) in map
        /*val polylineOptions = PolygonOptions()
            .add(LatLng(23.89, 86.0))
            .add(LatLng(23.99, 86.0)) // North of the previous point, but at the same longitude
            .add(LatLng(23.99, 86.2)) // Same latitude, and 30km to the west
            .strokeColor(Color.RED)
            .fillColor(Color.BLUE)


        // Get back the mutable Polygon
        val polygon = map.addPolygon(polylineOptions)

        // for clickable polygon
        polygon.isClickable = true
        // adding clicklistener in polygon
        map.setOnPolygonClickListener {
            Toast.makeText(this, "This is Clicked", Toast.LENGTH_SHORT).show()
        }*/


        // add circle in map
        // Instantiates a new CircleOptions object and defines the center and radius
        val circleOptions = CircleOptions()
            .center(LatLng(23.99497, 86.01111))
            .radius(100.0) // In meters
            .strokeWidth(10f)
            .strokeColor(Color.RED)
            .strokePattern(pattern)
            .fillColor(Color.BLUE)
            .clickable(true)

// Get back the mutable Circle
        val circle = map.addCircle(circleOptions)

        map.setOnCircleClickListener {
            // Flip the r, g and b components of the circle's stroke color.
            val strokeColor = it.strokeColor xor 0x00ffffff
            it.strokeColor = strokeColor
        }

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


