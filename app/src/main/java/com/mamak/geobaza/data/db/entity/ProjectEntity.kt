package com.mamak.geobaza.data.db.entity

import androidx.room.*
import com.mamak.geobaza.data.model.Point
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.model.ProjectState

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

    @ColumnInfo(name = "state")
    var state: ProjectState,

    @ColumnInfo(name = "receiveDate")
    var receiveDate: String?,

    @ColumnInfo(name = "processDate")
    var processDate: String?,

    @ColumnInfo(name = "markDate")
    var markDate: String?,

    @ColumnInfo(name = "measureDate")
    var measureDate: String?,

    @ColumnInfo(name = "outlineDate")
    var outlineDate: String?,

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
            state,
            receiveDate,
            processDate,
            markDate,
            measureDate,
            outlineDate,
            finishDate,
            note
        )
    }
}