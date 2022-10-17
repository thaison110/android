package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.DonveModel
import com.sonnv.kidsonline.model.HomieModel

data class CreateHomieRSP(
    @SerializedName("data")
    val data: List<HomieModel>
): BaseResponse()
