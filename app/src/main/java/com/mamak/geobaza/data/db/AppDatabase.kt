package com.mamak.geobaza.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mamak.geobaza.data.db.dao.PointDao
import com.mamak.geobaza.data.db.dao.ProjectDao
import com.mamak.geobaza.data.db.entity.Point
import com.mamak.geobaza.data.db.entity.Project

@Database(entities = [Project::class, Point::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun pointDao(): PointDao
}