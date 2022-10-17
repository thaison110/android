package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HealthRSP(
    @SerializedName("data")
    val data: List<HealthInfo>
) : BaseResponse()

data class HealthInfo(
    @SerializedName("date")
    val date: Long,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
) : Serializable
