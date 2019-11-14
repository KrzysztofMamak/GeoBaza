package com.mamak.geobaza.data.singleton

object AreaLab {
    private var areas = HashSet<String>()

    fun setAreas(areas: MutableList<String>) {
        areas.addAll(areas)
    }

    fun getAreas(): MutableList<String> {
        val areaList = mutableListOf<String>()
        areaList.addAll(areas)
        return areaList
    }
}