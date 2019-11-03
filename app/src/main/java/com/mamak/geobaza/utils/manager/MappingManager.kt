package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Project

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
}