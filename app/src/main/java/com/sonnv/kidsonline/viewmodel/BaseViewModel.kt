package com.sonnv.kidsonline.viewmodel

import androidx.lifecycle.ViewModel
import com.sonnv.kidsonline.service.APIModule
import com.sonnv.kidsonline.service.APIService

open class BaseViewModel() : ViewModel() {
    val apiService: APIService? = APIModule.getAPIService()
}