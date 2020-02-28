package com.mamak.geobaza.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Point (
    @SerializedName("name") @Expose var name: String,
    @SerializedName("x") @Expose var x: Double,
    @SerializedName("y") @Expose var y: Double
) : Parcelable