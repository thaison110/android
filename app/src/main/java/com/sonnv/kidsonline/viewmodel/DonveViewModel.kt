package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.sonnv.kidsonline.extension.convertImageToBase64
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.DonveModel
import com.sonnv.kidsonline.model.HomieModel
import com.sonnv.kidsonline.model.response.BaseResponse
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonveViewModel : BaseViewModel() {
    val donveHistoryLiveData: MediatorLiveData<List<DonveModel>> = MediatorLiveData()
    val homemieListLiveData: MediatorLiveData<List<HomieModel>> = MediatorLiveData()
    val donvePickupLiveData: MediatorLiveData<List<DonveModel>> = MediatorLiveData()
    val createHomieLiveData: MediatorLiveData<List<HomieModel>> = MediatorLiveData()
    val deleteHomieLiveData: MediatorLiveData<BaseResponse> = MediatorLiveData()

    fun getHistory(context: Context) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.getHistoryHomie(
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
                donveHistoryLiveData.value = it.body()?.data
            },
            onFailure = {
                donveHistoryLiveData.value = null
            }
        )
    }

    fun getHomieList(context: Context) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.getListHomie(
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
                homemieListLiveData.value = it.body()?.data
            },
            onFailure = {
                homemieListLiveData.value = null
            }
        )
    }

    fun sendPickUp(
        context: Context,
        note: String,
        type: Int,
        date: String,
        hour: String,
        homieId: String
    ) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.sendPickUp(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("type", type.toString()),
                    Pair("date", date),
                    Pair("hour", hour),
                    Pair("homie_id", homieId),
                    Pair("note", note),
                )
            ),
            time,
            note,
            type,
            date,
            hour,
            homieId
        )?.enqueueCustom(
            context,
            onResponse = {
                donvePickupLiveData.value = it.body()?.data
            },
            onFailure = {
                donvePickupLiveData.value = null
            }
        )
    }

    fun createHomie(context: Context, name: String, imageModel: String, relationShip: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            val token = PrefUtils.getInstance().getToken()
            val base64 = convertImageToBase64(imageModel)

            apiService?.createHomie(
                token,
                getChecksum(
                    arrayListOf(
                        Pair("token", token),
                        Pair("time", time.toString()),
                        Pair("name", name)
                    )
                ),
                time,
                "",
                name,
                "",
                base64,
                relationShip.toString()
            )?.enqueueCustom(
                context,
                onResponse = {
                    createHomieLiveData.value = it.body()?.data
                },
                onFailure = {
                    createHomieLiveData.value = null
                }
            )
        }
    }

    fun deleteHomie(context: Context, id: String) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.deleteHomie(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("id", id)
                )
            ),
            time,
            "",
            id
        )?.enqueueCustom(
            context,
            onResponse = {
                deleteHomieLiveData.value = it.body()
            },
            onFailure = {
                deleteHomieLiveData.value = null
            }
        )
    }
}