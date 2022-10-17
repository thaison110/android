package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AbsenceModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("dates")
    val dates: String,
    @SerializedName("status")
    val status: Int = 0
) : Serializable
