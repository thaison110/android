package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.AbsenceModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.response.BaseResponse
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import com.sonnv.kidsonline.util.getCurrentDateString

class AbsenceViewModel : BaseViewModel() {
    var activateLiveData: MediatorLiveData<List<TodayActivity>> = MediatorLiveData()
    var absenceHistoryLiveData: MediatorLiveData<List<AbsenceModel>> = MediatorLiveData()
    var absenceSendLiveData: MediatorLiveData<BaseResponse> = MediatorLiveData()


    fun getActivate(context: Context, currentDate: String = "") {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getActivate(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("date", currentDate.ifEmpty { getCurrentDateString() }),
                )
            ),
            time,
            currentDate
        )?.enqueueCustom(
            context,
            onResponse = {
                activateLiveData.value = it.body()?.data
            },
            onFailure = {
                activateLiveData.value = null
            }
        )
    }

    fun getAbsenceHistory(context: Context) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.historyAbsence(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString())
                )
            ),
            time
        )?.enqueueCustom(
            context,
            onResponse = {
                absenceHistoryLiveData.value = it.body()?.data
            },
            onFailure = {
                absenceHistoryLiveData.value = null
            })
    }

    fun sendAbsence(context: Context, note: String = "", data: List<CalendarDay>) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        val dateStr = data.joinToString {
            val month = if (it.month < 10) "0${it.month}" else it.month.toString()
            "${it.year}-$month-${it.day}"
        }

        apiService?.sendAbsence(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("dates", dateStr),
                    Pair("note", note),
                )
            ),
            time,
            note,
            dateStr
        )?.enqueueCustom(
            context,
            onResponse = {
                absenceSendLiveData.value = it.body()
            },
            onFailure = {
                absenceSendLiveData.value = null
            })
    }

}