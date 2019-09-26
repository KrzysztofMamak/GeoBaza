package com.mamak.geobaza.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Project(
    @SerializedName("number") @Expose var number: Int? = 0,
    @SerializedName("area") @Expose var area: String? = null,
    @SerializedName("town") @Expose var town: String? = null,
    @SerializedName("street") @Expose var street: String? = null,
    @SerializedName("description") @Expose var description: String? = null,
    @SerializedName("pointList") @Expose var pointList: List<Point>? = null,
    @SerializedName("isMarked") @Expose var isMarked: Boolean,
    @SerializedName("isMeasured") @Expose var isMeasured: Boolean,
    @SerializedName("isDone") @Expose var isDone: Boolean,
    @SerializedName("startDate") @Expose var startDate: String? = null,
    @SerializedName("markDate") @Expose var markDate: String? = null,
    @SerializedName("measureDate") @Expose var measureDate: String? = null,
    @SerializedName("doneDate") @Expose var doneDate: String? = null,
    var distance: Float? = 0F
)