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

    @ColumnInfo(name = "isProcessed")
    var isProcessed: Boolean,

    @ColumnInfo(name = "isMarked")
    var isMarked: Boolean,

    @ColumnInfo(name = "isMeasured")
    var isMeasured: Boolean,

    @ColumnInfo(name = "isFinished")
    var isFinished: Boolean,

    @ColumnInfo(name = "startDate")
    var startDate: String?,

    @ColumnInfo(name = "processDate")
    var processDate: String?,

    @ColumnInfo(name = "markDate")
    var markDate: String?,

    @ColumnInfo(name = "measureDate")
    var measureDate: String?,

    @ColumnInfo(name = "finishDate")
    var finishDate: String?,

    @ColumnInfo(name = "note")
    var note: String?
) {
    fun toProject(): Project {
        return Project(
            number,
            area,
            town,
            street,
            description,
            pointList,
            isProcessed,
            isMarked,
            isMeasured,
            isFinished,
            startDate,
            processDate,
            markDate,
            measureDate,
            finishDate,
            note
        )
    }
}