package com.mamak.geobaza.data.db.dao

import androidx.room.*
import com.mamak.geobaza.data.db.entity.Project

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Delete
    fun delete(project: Project)

    @Query("SELECT * FROM projects")
    fun getAllProjects(): List<Project>

    @Query("SELECT * FROM projects WHERE number=:number")
    fun getProjectByNumber(number: Int): List<Project>
}