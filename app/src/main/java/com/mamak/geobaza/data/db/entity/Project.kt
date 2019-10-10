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

    @ColumnInfo(name = "is_marked")
    var isMarked: Boolean,

    @ColumnInfo(name = "is_measured")
    var isMeasured: Boolean,

    @ColumnInfo(name = "is_done")
    var isDone: Boolean,

    @ColumnInfo(name = "start_date")
    var startDate: String?,

    @ColumnInfo(name = "mark_date")
    var markDate: String?,

    @ColumnInfo(name = "measure_date")
    var measureDate: String?,

    @ColumnInfo(name = "done_date")
    var doneDate: String?
)