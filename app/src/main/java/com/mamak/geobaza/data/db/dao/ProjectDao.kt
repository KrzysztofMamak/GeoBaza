package com.mamak.geobaza.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.mamak.geobaza.data.db.entity.Project

@Dao
interface ProjectDao {
    @Insert
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Delete
    fun delete(project: Project)
}