package com.mamak.geobaza.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mamak.geobaza.data.model.ProjectState

class ProjectStateConverter {
    @TypeConverter
    fun fromProjectState(value: ProjectState): String {
        val gson = Gson()
        val type = object : TypeToken<ProjectState>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toProjectState(value: String): ProjectState {
        val gson = Gson()
        val type = object : TypeToken<ProjectState>() {}.type
        return gson.fromJson(value, type)
    }
}