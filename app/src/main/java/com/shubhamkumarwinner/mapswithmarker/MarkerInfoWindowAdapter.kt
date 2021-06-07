package com.shubhamkumarwinner.mapswithmarker

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.lang.Exception

class MarkerInfoWindowAdapter(
    private val context: Context,
    private val latLng: LatLng,
    private val address: String
) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker?): View? {

        // 2. Inflate view and set title, address, and rating
        val view = LayoutInflater.from(context).inflate(
            R.layout.marker_info_contents, null
        )
        view.findViewById<TextView>(
            R.id.text_view_title
        ).text = "My Home"
        view.findViewById<TextView>(
            R.id.text_view_address
        ).text = address
        view.findViewById<TextView>(
            R.id.text_view_rating
        ).text = "This is My Home address"

        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        // Return null to indicate that the
        // default window (white bubble) should be used
        return null
    }
}