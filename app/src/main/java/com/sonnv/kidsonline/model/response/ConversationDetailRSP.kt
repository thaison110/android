package com.sonnv.kidsonline.model.response

import com.google.gson.annotations.SerializedName
import com.sonnv.kidsonline.model.ChatMessage
import com.sonnv.kidsonline.model.Conversation
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo

data class ConversationDetailRSP(
    @SerializedName("data")
    val data: List<ChatMessage>
): BaseResponse()

