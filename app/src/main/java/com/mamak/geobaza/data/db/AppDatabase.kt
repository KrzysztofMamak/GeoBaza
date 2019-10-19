package com.mamak.geobaza.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mamak.geobaza.data.db.converter.PointConverter
import com.mamak.geobaza.data.db.dao.ProjectDao
import com.mamak.geobaza.data.db.entity.ProjectEntity

@Database(entities = [ProjectEntity::class], version = 1, exportSchema = false)
@TypeConverters(PointConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}