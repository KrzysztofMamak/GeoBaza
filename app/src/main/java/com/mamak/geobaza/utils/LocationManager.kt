package com.mamak.geobaza.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mamak.geobaza.R

//TODO Refactor
object LocationManager {
    fun navigateByGeoCoordinates(context: Context, x: Double?, y: Double?) {
        val geoCoordinates = CoordinatesConverter.tr2000WGS(
                x?.let {
                    y?.let {
                            it1 -> doubleArrayOf(it, it1)
                    }
                }).asList()
        val intentUri = Uri.parse(context.getString(R.string.google_maps_query) +
                geoCoordinates[0] + "," +
                geoCoordinates[1])
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage(context.getString(R.string.google_maps_package))
        context.startActivity(mapIntent)
    }
}