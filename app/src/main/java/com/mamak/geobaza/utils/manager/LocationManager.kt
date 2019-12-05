package com.mamak.geobaza.utils.manager

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.singleton.LocationLab

object LocationManager {
    fun navigateByGeoCoordinates(context: Context, x: Double, y: Double) {
        val geoCoordinates = CoordinatesManager.tr2000WGS(doubleArrayOf(x, y)).asList()
        val intentUri = Uri.parse(context.getString(R.string.google_maps_query) +
                geoCoordinates[0] + "," + geoCoordinates[1])
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage(context.getString(R.string.google_maps_package))
        context.startActivity(mapIntent)
    }

    fun calculateDistance(point: Point): Float? {
        val location = Location("destLocation")
        val geoCoordinates = CoordinatesManager.tr2000WGS(doubleArrayOf(point.x, point.y)).asList()
        location.latitude = geoCoordinates[0]
        location.longitude = geoCoordinates[1]
        return LocationLab.getCurrentLocation()?.distanceTo(location)?.div(1000)
    }
}