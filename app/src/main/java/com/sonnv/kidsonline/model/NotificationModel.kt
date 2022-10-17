package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("item_id")
    val itemId: Int,
) : Serializable
