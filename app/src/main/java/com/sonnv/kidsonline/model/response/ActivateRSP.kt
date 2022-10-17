package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo

data class ActivateRSP(
    @SerializedName("data")
    val data: List<TodayActivity>
): BaseResponse()

