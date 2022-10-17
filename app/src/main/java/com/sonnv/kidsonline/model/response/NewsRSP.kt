package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.*
import java.io.Serializable

data class NewsRSP(
    @SerializedName("data")
    val data: List<NewsModel>
): BaseResponse()
