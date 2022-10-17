package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName

data class DonveModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("hour")
    val hour: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("homie_name")
    val homieName: String,
    @SerializedName("relationship")
    val relationShip: String
)