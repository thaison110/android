package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.DonveModel

data class DonveHistoryRSP(
    @SerializedName("data")
    val data: List<DonveModel>
): BaseResponse()
