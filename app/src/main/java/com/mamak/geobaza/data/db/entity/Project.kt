package com.mamak.geobaza.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey
    @ColumnInfo(name = "number")
    var number: Int,

    @ColumnInfo(name = "area")
    var area: String?,

    @ColumnInfo(name = "town")
    var town: String?,

    @ColumnInfo(name = "street")
    var street: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "isMarked")
    var isMarked: Boolean,

    @ColumnInfo(name = "isMeasured")
    var isMeasured: Boolean,

    @ColumnInfo(name = "isDone")
    var isDone: Boolean,

    @ColumnInfo(name = "startDate")
    var startDate: String?,

    @ColumnInfo(name = "markDate")
    var markDate: String?,

    @ColumnInfo(name = "measureDate")
    var measureDate: String?,

    @ColumnInfo(name = "doneDate")
    var doneDate: String?
)