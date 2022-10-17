package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.Conversation
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo

data class ConversationRSP(
    @SerializedName("data")
    val data: List<Conversation>
): BaseResponse()

