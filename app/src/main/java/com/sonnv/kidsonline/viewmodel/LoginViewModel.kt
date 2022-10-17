package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.model.response.LoginData
import com.sonnv.kidsonline.model.response.LoginRSP
import com.sonnv.kidsonline.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : BaseViewModel() {
    var loginLiveData: MediatorLiveData<LoginData> = MediatorLiveData()

    fun login(context: Context?, userName: String = "", passWord: String = "", isSave: Boolean = false) {
        if (isSave) {
            PrefUtils.getInstance().saveData(USER_NAME, userName)
            PrefUtils.getInstance().saveData(PASS_WORD, passWord)
        }

        val time = System.currentTimeMillis()
        apiService?.login(
            userName,
            passWord,
            getChecksum(arrayListOf(
                Pair("username", userName),
                Pair("password", passWord),
                Pair("time", time.toString())
            )),
            time
        )?.enqueue(object : Callback<LoginRSP> {
            override fun onResponse(call: Call<LoginRSP>, response: Response<LoginRSP>) {
                val token = response.body()?.data?.token
                // save token to local
                token?.let {
                    PrefUtils.getInstance().saveData(TOKEN, it)
                }
                response.body()?.data?.userInfo?.let {
                    PrefUtils.getInstance().saveData(USER_INFO, Gson().toJson(it))
                }

                loginLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<LoginRSP>, t: Throwable) {
                loginLiveData.value = null
            }

        })
    }
}