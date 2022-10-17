package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Conversation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val userName: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("text_last")
    val shortText: String,
    @SerializedName("time_last")
    val time: String,
    @SerializedName("sort")
    val sort: Int
): Serializable
