package com.sonnv.kidsonline

import android.app.Application

class KidsApplication: Application() {

    companion object {
        lateinit var instance: KidsApplication
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}