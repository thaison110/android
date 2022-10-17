package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sonnv.kidsonline.extension.convertImageToBase64
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.ImageModel
import com.sonnv.kidsonline.model.MedicineModel
import com.sonnv.kidsonline.model.MedicineTicketModel
import com.sonnv.kidsonline.model.response.BaseResponse
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicineViewModel : BaseViewModel() {
    val medicineHistoryLiveData: MediatorLiveData<List<MedicineTicketModel>> = MediatorLiveData()
    val deleteMedicineLiveData: MediatorLiveData<BaseResponse> = MediatorLiveData()
    val sendMedicineLiveData: MediatorLiveData<BaseResponse> = MediatorLiveData()

    fun getHistoryMedicine(context: Context) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.historyMedicine(
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
                medicineHistoryLiveData.value = it.body()?.data
            },
            onFailure = {
                medicineHistoryLiveData.value = null
            }
        )
    }

    fun deleteMedicine(context: Context, id: String, note: String) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.deleteMedicine(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("id", id)
                )
            ),
            note,
            id
        )?.enqueueCustom(
            context,
            onResponse = {
                deleteMedicineLiveData.value = it.body()
            },
            onFailure = {
                deleteMedicineLiveData.value = null
            }
        )
    }

    fun sendMedicine(
        context: Context,
        startDate: String,
        endDate: String,
        images: List<ImageModel>,
        noteDetail: List<MedicineModel>,
        note: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            val token = PrefUtils.getInstance().getToken()
            val detail = Gson().toJson(noteDetail)
            val imagesBase64: MutableList<String> = arrayListOf()

            images.forEach {
                val path = it.getPathFromUri(context)
                path?.let {
                    val base64 = convertImageToBase64(path)
                    imagesBase64.add(base64)
                }
            }

            val imageBase64Str = Gson().toJson(imagesBase64)

            apiService?.sendMedicine(
                token,
                getChecksum(
                    arrayListOf(
                        Pair("token", token),
                        Pair("time", time.toString()),
                        Pair("date_start", startDate),
                        Pair("date_end", endDate),
                        Pair("detail", detail),
                        Pair("note", note)
                    )
                ),
                time,
                note,
                startDate,
                endDate,
                imageBase64Str,
                detail
            )?.enqueueCustom(
                context,
                onResponse = {
                    sendMedicineLiveData.value = it.body()
                },
                onFailure = {
                    sendMedicineLiveData.value = null
                }
            )
        }
    }
}