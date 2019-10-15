package com.mamak.geobaza.data.db.dao

import androidx.room.*
import com.mamak.geobaza.data.db.entity.Point

@Dao
interface PointDao {
//    TODO check onConflict - name
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(point: Point)

    @Update
    fun update(point: Point)

    @Delete
    fun delete(point: Point)

    @Query("SELECT * FROM points WHERE projectNumber=:projectNumber")
    fun getPointsByProjectNumber(projectNumber: Int): List<Point>
}