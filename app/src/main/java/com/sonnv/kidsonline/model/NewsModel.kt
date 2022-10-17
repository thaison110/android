package com.sonnv.kidsonline.model

import com.google.gson.annotations.SerializedName

class NewsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("time")
    val time: Long,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("is_important")
    val isImportant: Int,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("detail")
    val detail: String
) : BaseFeed() {
    override fun getFeedType(): Int {
        return FeedType.NEWS_SCHOOL
    }
}
