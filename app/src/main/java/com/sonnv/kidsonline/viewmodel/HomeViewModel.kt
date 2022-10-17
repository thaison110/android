package com.sonnv.kidsonline.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.sonnv.kidsonline.extension.enqueueCustom
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.util.getChecksum
import com.sonnv.kidsonline.util.getCurrentDateString

class HomeViewModel : BaseViewModel() {
    var userInfoLiveData: MediatorLiveData<UserInfo> = MediatorLiveData()

    fun getUserInfo() {
        userInfoLiveData.value = PrefUtils.getInstance().getUserInfo()
    }
}