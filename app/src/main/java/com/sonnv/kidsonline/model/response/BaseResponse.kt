package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status")
    val status: Int ?= -1,
    @SerializedName("message")
    val message: String ?= null
)
