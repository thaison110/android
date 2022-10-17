package com.sonnv.kidsonline.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.NotificationModel
import com.sonnv.kidsonline.model.response.NotificationRSP
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel : BaseViewModel() {
    val listNotifyLiveData: MediatorLiveData<List<NotificationModel>> = MediatorLiveData()
    val seenAllNotifyLiveData: MediatorLiveData<Boolean> = MediatorLiveData()

    fun getListNotify() {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getListNotify(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString())
                )
            ),
            time
        )?.enqueue(object : Callback<NotificationRSP> {
            override fun onResponse(
                call: Call<NotificationRSP>,
                response: Response<NotificationRSP>
            ) {
                listNotifyLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<NotificationRSP>, t: Throwable) {
                listNotifyLiveData.value = null
            }

        })
    }

    fun seenAllNotify() {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.seenAllNotify(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString())
                )
            ),
            time
        )?.enqueueCustom(context,
            onResponse = {
                seenAllNotifyLiveData.value = it.body()?.status == 200
            },
            onFailure = {
                seenAllNotifyLiveData.value = false
            }
        )
    }
}