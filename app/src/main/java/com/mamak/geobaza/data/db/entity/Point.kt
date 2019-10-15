package com.mamak.geobaza.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "points",
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["number"],
            childColumns = ["projectNumber"],
            onDelete = CASCADE
        )
    ]
)
data class Point(
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "x")
    var x: Double?,

    @ColumnInfo(name = "y")
    var y: Double?,

    @ColumnInfo(name = "projectNumber", index = true)
    var projectNumber: Int?
)