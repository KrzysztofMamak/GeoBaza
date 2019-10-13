package com.mamak.geobaza.data.singleton

import android.location.Location
import com.mamak.geobaza.data.model.Project

object ProjectLab {
    private var projects = mutableListOf<Project>()
    private var currentLocation: Location? = null

    fun setProjects(projects: List<Project>) {
        ProjectLab.projects = projects.toMutableList()
    }

    fun setCurrentLocation(currentLocation: Location?) {
        this.currentLocation = currentLocation
    }

    fun getAllProjects(): MutableList<Project> = projects

    fun getProject(number: Int): Project? {
        return projects.find {
            it.number == number
        }
    }

    fun getCurrentLocation(): Location? {
        return currentLocation
    }
}