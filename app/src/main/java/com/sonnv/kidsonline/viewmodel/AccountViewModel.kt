package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sonnv.kidsonline.extension.convertImageToBase64
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.model.response.HealthInfo
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.USER_INFO
import com.sonnv.kidsonline.util.getChecksum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Year
import java.util.*

class AccountViewModel : BaseViewModel() {
    val studentInfoLiveData: MediatorLiveData<UserInfo> = MediatorLiveData()
    val parentInfoLiveData: MediatorLiveData<UserInfo> = MediatorLiveData()
    val changePasswordLiveData: MediatorLiveData<Boolean> = MediatorLiveData()
    val healthInfoLiveData: MediatorLiveData<List<HealthInfo>> = MediatorLiveData()

    fun changeStudentInfo(
        context: Context,
        name: String,
        nickName: String,
        birthday: String,
        gender: Int,
        image: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            val token = PrefUtils.getInstance().getToken()
            val base64Image = if (image.isNullOrEmpty()) "" else convertImageToBase64(image)

            apiService?.changeStudentInfo(
                token,
                getChecksum(
                    arrayListOf(
                        Pair("token", token),
                        Pair("time", time.toString()),
                        Pair("name", name),
                        Pair("nickname", nickName),
                        Pair("birthday", birthday),
                        Pair("gender", gender.toString())
                    )
                ),
                time, name, nickName, birthday, gender, base64Image
            )?.enqueueCustom(context,
                onResponse = {
                    val userInfo = it.body()?.data?.userInfo
                    studentInfoLiveData.value = userInfo
                    if (userInfo != null) {
                        PrefUtils.getInstance().saveData(USER_INFO, Gson().toJson(userInfo))
                    }
                },
                onFailure = {
                    studentInfoLiveData.value = null
                }
            )
        }
    }

    fun changeParentInfo(
        context: Context,
        name: String,
        phone: String,
        birthday: String,
        email: String,
        address: String,
    ) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.changeParentInfo(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("parent_name", name),
                    Pair("parent_birthday", birthday),
                    Pair("tel", phone),
                    Pair("address", address),
                    Pair("email", email)
                )
            ),
            time, name, address, birthday, email, phone
        )?.enqueueCustom(context,
            onResponse = {
                parentInfoLiveData.value = it.body()?.data?.userInfo
            },
            onFailure = {
                parentInfoLiveData.value = null
            }
        )
    }

    fun changePassword(
        context: Context,
        password: String,
        newPassword: String,
        newPasswordRetype: String
    ) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.changePassword(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("password", password),
                    Pair("password_new", newPassword),
                    Pair("password_new_retype", newPasswordRetype),
                )
            ),
            time, password, newPassword, newPasswordRetype
        )?.enqueueCustom(context,
            onResponse = {
                changePasswordLiveData.value = it.body()?.status == 200
            },
            onFailure = {
                changePasswordLiveData.value = false
            }
        )
    }

    fun getHealthInfo(context: Context, year: Int = Calendar.getInstance().get(Calendar.YEAR)) {
        val time = System.currentTimeMillis()
        val token = PrefUtils.getInstance().getToken()

        apiService?.getHealthInfo(
            token,
            getChecksum(
                arrayListOf(
                    Pair("token", token),
                    Pair("time", time.toString()),
                    Pair("year", year.toString())
                )
            ),
            time, year
        )?.enqueueCustom(context,
            onResponse = {
                healthInfoLiveData.value = it.body()?.data
            },
            onFailure = {
                healthInfoLiveData.value = null
            }
        )
    }
}