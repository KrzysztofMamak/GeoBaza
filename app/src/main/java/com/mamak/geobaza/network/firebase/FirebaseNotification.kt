package com.mamak.geobaza.network.firebase

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseNotification(
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("body")
    @Expose
    var body: String
)