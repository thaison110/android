package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomieModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tel")
    val telephone: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("relationship")
    val relationShip: Int
): Serializable
