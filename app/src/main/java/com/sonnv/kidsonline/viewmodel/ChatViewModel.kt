package com.sonnv.kidsonline.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.sonnv.kidsonline.model.ChatMessage
import com.sonnv.kidsonline.model.Conversation
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.model.response.ActivateRSP
import com.sonnv.kidsonline.model.response.ConversationDetailRSP
import com.sonnv.kidsonline.model.response.ConversationRSP
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import com.sonnv.kidsonline.util.getCurrentDateString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel: BaseViewModel() {
    var conversationLiveData: MediatorLiveData<List<Conversation>> = MediatorLiveData()
    var conversationDetailLiveData: MediatorLiveData<List<ChatMessage>> = MediatorLiveData()

    fun getTeacherList() {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getTeacherList(
            token,
            getChecksum(arrayListOf(
                Pair("token", token),
                Pair("time", time.toString())
            )),
            time
        )?.enqueue(object : Callback<ConversationRSP> {
            override fun onResponse(call: Call<ConversationRSP>, response: Response<ConversationRSP>) {
                conversationLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<ConversationRSP>, t: Throwable) {
                conversationLiveData.value = null
            }

        })
    }

    fun getChatHistory(teacherId: Int) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getChatHistory(
            token,
            getChecksum(arrayListOf(
                Pair("token", token),
                Pair("time", time.toString()),
                Pair("teacher_id", teacherId.toString())
            )),
            time,
            teacherId
        )?.enqueue(object : Callback<ConversationDetailRSP> {
            override fun onResponse(call: Call<ConversationDetailRSP>, response: Response<ConversationDetailRSP>) {
                conversationDetailLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<ConversationDetailRSP>, t: Throwable) {
                conversationDetailLiveData.value = null
            }

        })
    }

    fun sendMessage(message: String, teacherId: Int) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.sendMessage(
            token,
            getChecksum(arrayListOf(
                Pair("token", token),
                Pair("time", time.toString()),
                Pair("teacher_id", teacherId.toString()),
                Pair("message", message)
            )),
            time,
            message,
            teacherId
        )?.enqueue(object : Callback<ConversationDetailRSP> {
            override fun onResponse(call: Call<ConversationDetailRSP>, response: Response<ConversationDetailRSP>) {
                conversationDetailLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<ConversationDetailRSP>, t: Throwable) {
                conversationDetailLiveData.value = null
            }

        })
    }
}