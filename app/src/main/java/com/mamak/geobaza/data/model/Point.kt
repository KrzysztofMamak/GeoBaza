package com.mamak.geobaza.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Point (
    @SerializedName("name") @Expose var name: String,
    @SerializedName("x") @Expose var x: Double,
    @SerializedName("y") @Expose var y: Double
)