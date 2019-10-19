package com.mamak.geobaza.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mamak.geobaza.data.model.Point

class PointConverter {
    @TypeConverter
    fun fromPointList(value: List<Point>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Point>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPointList(value: String): List<Point> {
        val gson = Gson()
        val type = object : TypeToken<List<Point>>() {}.type
        return gson.fromJson(value, type)
    }
}