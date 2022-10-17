package com.sonnv.kidsonline.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.response.NewDetailRSP
import com.sonnv.kidsonline.model.response.NewsRSP
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : BaseViewModel() {
    val listNewsLiveData: MediatorLiveData<List<NewsModel>> = MediatorLiveData()
    val newDetailLiveData: MediatorLiveData<NewsModel> = MediatorLiveData()

    fun getListNews() {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getListNews(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString())
                )
            ),
            time
        )?.enqueue(object : Callback<NewsRSP> {
            override fun onResponse(
                call: Call<NewsRSP>,
                response: Response<NewsRSP>
            ) {
                listNewsLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<NewsRSP>, t: Throwable) {
                listNewsLiveData.value = null
            }

        })
    }

    fun getNewDetail(id: Int) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()
        apiService?.getNewDetail(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("id", id.toString())
                )
            ),
            time,
            id
        )?.enqueue(object : Callback<NewDetailRSP> {
            override fun onResponse(call: Call<NewDetailRSP>, response: Response<NewDetailRSP>) {
                newDetailLiveData.value = response.body()?.data
            }

            override fun onFailure(call: Call<NewDetailRSP>, t: Throwable) {
                newDetailLiveData.value = null
            }

        })
    }
}