package com.mamak.geobaza.data.singleton

import android.location.Location

object LocationLab {
    private var currentLocation: Location? = null

    fun setCurrentLocation(currentLocation: Location?) {
        this.currentLocation = currentLocation
    }

    fun getCurrentLocation(): Location? {
        return currentLocation
    }
}