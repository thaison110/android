package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.MedicineTicketModel

data class MedicineRSP(
    @SerializedName("data")
    val data: List<MedicineTicketModel>
): BaseResponse()
