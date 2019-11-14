package com.mamak.geobaza.data.singleton

object AreaLab {
    private var areas = mutableListOf<String>()

    fun setAreas(areas: HashSet<String>) {
        areas.addAll(areas)
    }

    fun getAreas(): MutableList<String> {
        return areas
    }
}