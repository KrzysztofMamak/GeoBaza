package com.mamak.geobaza.data.db.entity

import androidx.room.*
import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.model.Project

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey
    @ColumnInfo(name = "number")
    var number: Int,

    @ColumnInfo(name = "area")
    var area: String,

    @ColumnInfo(name = "town")
    var town: String,

    @ColumnInfo(name = "street")
    var street: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "pointList")
    var pointList: List<Point>,

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
) {
    fun toProject(): Project {
        return Project(
            number,
            area,
            town,
            street,
            description,
            pointList,
            isMarked,
            isMeasured,
            isDone,
            startDate,
            markDate,
            measureDate,
            doneDate
        )
    }
}