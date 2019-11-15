package com.mamak.geobaza.data.singleton

object AreaLab {
    private var areas = HashSet<String>()

    fun setAreas(areas: MutableList<String>) {
        this.areas.addAll(areas)
    }

    fun getAreas(): MutableList<String> {
        return areas.toMutableList()
    }
}