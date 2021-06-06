package com.shubhamkumarwinner.mapswithmarker

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerDragListener, OnInfoWindowClickListener {
    private val REQUEST_LOCATION_PERMISSION = 1

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var map: GoogleMap

    var latitude = /*23.994773296272992*/ 24.00366556097543
    var longitude = /*86.01104959845541*/ 86.01206984370948
    var markerDumri: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val zoomLevel = 15f
        val givenLatLng = LatLng(latitude, longitude)

        // for adding custom info window
        map.setInfoWindowAdapter(MarkerInfoWindowAdapter(this, givenLatLng))
        markerDumri = map.addMarker(
            MarkerOptions()
                .position(givenLatLng)
                .title("Marker in Dumri")
                .snippet("Latitude: $latitude Longitude: $longitude")
                .draggable(true)
        )

        // for enabling zoom controller in map
        map.uiSettings.isZoomControlsEnabled = true

        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        markerDumri!!.tag = 0
        map.isTrafficEnabled = true

        //adding marker drag listener
        map.setOnMarkerDragListener(this)

        //adding info window clickListener
        map.setOnInfoWindowClickListener(this)

        // for getting users last location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val myLatLang = LatLng(it.latitude, it.longitude)
                val distance: Float = it.distanceTo(Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = givenLatLng.latitude
                    longitude = givenLatLng.longitude
                })
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLang, zoomLevel))
                Log.d(TAG, "Last location's latitude is : ${it.latitude} and longitude is ${it.longitude}")
                Log.d(TAG, "Distance is $distance m")
            }
        }

        enableMyLocation()
    }

    override fun onMarkerDragStart(marker: Marker) {
        val position = marker.position
        Log.d("Drag", "${position.latitude}  ${position.longitude}")
    }

    override fun onMarkerDrag(marker: Marker) {
        /**
         * not necessary
         */
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


    // Check the user permission
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    // on request permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
        }
    }
}


