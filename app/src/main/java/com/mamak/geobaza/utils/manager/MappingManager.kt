package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.model.Project
import org.osmdroid.util.GeoPoint

object MappingManager {
    fun projectToFieldList(project: Project?): MutableList<Pair<String, String?>> {
        val fieldList = mutableListOf<Pair<String, String?>>()
        fieldList.apply {
            add(Pair("Numer projektu", project?.number.toString()))
            add(Pair("Powiat", project?.area))
            add(Pair("Miasto", project?.town))
            add(Pair("Ulica", project?.street))
            add(Pair("Opis", project?.description))
            add(Pair("Ilość punktów", project?.pointList?.size.toString()))
            add(Pair("Data otrzymania", project?.startDate))
            add(Pair("Data przetworzenia", project?.processDate))
            add(Pair("Data wytyczenia", project?.markDate))
            add(Pair("Data pomiaru", project?.measureDate))
            add(Pair("Data zakończenia", project?.number.toString()))
            add(Pair("Notatka", project?.note))
        }
        return fieldList
    }

    fun pointToGeoPoint(point: Point): GeoPoint {
        val geoCoordinates = CoordinatesManager.tr2000WGS(doubleArrayOf(point.x, point.y)).asList()
        return GeoPoint(geoCoordinates[0], geoCoordinates[1])
    }
}