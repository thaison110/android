package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.DonveModel

data class DonveDeleteRSP(
    @SerializedName("data")
    val data: List<DonveModel>
): BaseResponse()
