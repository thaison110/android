package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.AbsenceModel
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import java.io.Serializable

data class AbsenceHistoryRSP(
    @SerializedName("data")
    val data: List<AbsenceModel>
): BaseResponse()
