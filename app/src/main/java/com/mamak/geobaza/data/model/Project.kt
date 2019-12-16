package com.mamak.geobaza.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mamak.geobaza.data.db.entity.ProjectEntity

class Project(
    @SerializedName("number") @Expose var number: Int = 0,
    @SerializedName("area") @Expose var area: String,
    @SerializedName("town") @Expose var town: String,
    @SerializedName("street") @Expose var street: String,
    @SerializedName("description") @Expose var description: String,
    @SerializedName("pointList") @Expose var pointList: List<Point>,
    @SerializedName("state") @Expose var state: ProjectState,
    @SerializedName("startDate") @Expose var startDate: String? = null,
    @SerializedName("processDate") @Expose var processDate: String? = null,
    @SerializedName("markDate") @Expose var markDate: String? = null,
    @SerializedName("measureDate") @Expose var measureDate: String? = null,
    @SerializedName("finishDate") @Expose var finishDate: String? = null,
    @SerializedName("note") @Expose var note: String? = null
) {
    fun toProjectEntity(): ProjectEntity {
        return ProjectEntity(
            number,
            area,
            town,
            street,
            description,
            pointList,
            state,
            startDate,
            processDate,
            markDate,
            measureDate,
            finishDate,
            note
        )
    }
}