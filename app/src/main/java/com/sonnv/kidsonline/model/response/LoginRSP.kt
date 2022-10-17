package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import java.io.Serializable

data class LoginRSP(
    @SerializedName("data")
    val data: LoginData
): BaseResponse()

data class LoginData(
    @SerializedName("user_info")
    val userInfo: UserInfo,
    @SerializedName("token")
    val token: String,
    @SerializedName("activate")
    val activates: List<TodayActivity>,
    @SerializedName("news")
    val newsList: List<NewsModel>
): Serializable
