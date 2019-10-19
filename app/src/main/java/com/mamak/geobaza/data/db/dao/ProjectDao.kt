package com.mamak.geobaza.data.db.dao

import androidx.room.*
import com.mamak.geobaza.data.db.entity.ProjectEntity

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectEntity: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectEntityList: List<ProjectEntity>)

    @Update
    fun update(projectEntity: ProjectEntity)

    @Delete
    fun delete(projectEntity: ProjectEntity)

    @Query("SELECT * FROM projects")
    fun getAllProjects(): List<ProjectEntity>

    @Query("SELECT * FROM projects WHERE number=:number")
    fun getProjectByNumber(number: Int): List<ProjectEntity>
}