package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Point
import org.osmdroid.util.BoundingBox

object OsmManager {
    fun createBoundingBoxFromPointList(pointList: MutableList<Point>): BoundingBox {
        val boundingBox = BoundingBox()
        var north = Double.MIN_VALUE
        var east = Double.MIN_VALUE
        var south = Double.MAX_VALUE
        var west = Double.MAX_VALUE
        pointList.map { MappingManager.pointToGeoPoint(it) }.forEach {
            north = if (it.latitude > north) it.latitude else north
            east = if (it.longitude > east) it.longitude else east
            south = if (it.latitude < south) it.latitude else south
            west = if (it.longitude < west) it.longitude else west
        }
        boundingBox.set(north, east, south, west)
        return boundingBox
    }
}