package com.mamak.geobaza.data.singleton

import android.location.Location
import com.mamak.geobaza.data.model.Project

object ProjectLab {
    private var currentLocation: Location? = null

    fun setCurrentLocation(currentLocation: Location?) {
        this.currentLocation = currentLocation
    }

    fun getCurrentLocation(): Location? {
        return currentLocation
    }
}