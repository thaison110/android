package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import java.io.Serializable

data class ChangeUserInfoRSP(
    @SerializedName("data")
    val data: InfoData
): BaseResponse()

data class InfoData(
    @SerializedName("user_info")
    val userInfo: UserInfo,
): Serializable
