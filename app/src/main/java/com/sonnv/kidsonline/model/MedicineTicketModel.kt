package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MedicineTicketModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("date_start")
    val startDate: String,
    @SerializedName("date_end")
    val endDate: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("image")
    val images: List<String>,
    @SerializedName("status")
    val status: Int,
    @SerializedName("teacher_confirm")
    val confirmBy: String
): Serializable
