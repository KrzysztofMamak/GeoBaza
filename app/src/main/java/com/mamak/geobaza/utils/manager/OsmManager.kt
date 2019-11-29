package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Point
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint

object OsmManager {
    fun getBoundingBoxByPointList(pointList: MutableList<Point>): BoundingBox {
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

    fun getAveragePointFromList(pointList: MutableList<Point>): GeoPoint {
        var latitudeSum: Double = 0.0
        var longitudeSum: Double = 0.0
        pointList.map { MappingManager.pointToGeoPoint(it) }.forEach {
            latitudeSum += it.latitude
            longitudeSum += it.longitude
        }
        return GeoPoint(latitudeSum / pointList.size, longitudeSum / pointList.size)
    }
}