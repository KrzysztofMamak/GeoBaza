package com.mamak.geobaza.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Point (
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("x") @Expose var x: Double? = 0.0,
    @SerializedName("y") @Expose var y: Double? = 0.0
)