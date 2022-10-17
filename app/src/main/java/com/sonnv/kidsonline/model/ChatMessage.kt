package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("sender_id")
    val senderId: Int,
    val type: Int,
    @SerializedName("detail")
    val content: String,
    @SerializedName("time")
    val time: String
)
