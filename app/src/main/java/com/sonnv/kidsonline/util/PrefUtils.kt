package com.sonnv.kidsonline.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sonnv.kidsonline.KidsApplication
import com.sonnv.kidsonline.model.UserInfo

class PrefUtils(context: Context) {
    companion object {
        const val PREF_NAME = "PREF_KIDS"
        var mPrefUtils: PrefUtils? = null

        fun getInstance(context: Context = KidsApplication.instance): PrefUtils {
            return mPrefUtils ?: PrefUtils(context)
        }
    }

    private var mPreferences: SharedPreferences? = null

    init {
        mPreferences =
            context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: Any) {
        val editor = mPreferences?.edit()
        when (value) {
            is Int -> editor?.putInt(key, value)
            is String -> editor?.putString(key, value)
            is Float -> editor?.putFloat(key, value)
            is Long -> editor?.putLong(key, value)
            is Boolean -> editor?.putBoolean(key, value)
        }
        editor?.apply()
    }

    fun getValue(key: String, defaultValue: Int): Int {
        return mPreferences?.getInt(key, defaultValue) ?: defaultValue
    }

    fun getValue(key: String, defaultValue: String): String {
        return mPreferences?.getString(key, defaultValue) ?: defaultValue
    }

    fun getValue(key: String, defaultValue: Long): Long {
        return mPreferences?.getLong(key, defaultValue) ?: defaultValue
    }

    fun getValue(key: String, defaultValue: Float): Float {
        return mPreferences?.getFloat(key, defaultValue) ?: defaultValue
    }

    fun getValue(key: String, defaultValue: Boolean): Boolean {
        return mPreferences?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun clearAll() {
        mPreferences?.edit()?.clear()?.apply()
    }

    fun getUserInfo(): UserInfo? {
        return try {
            val userInfo: String = getValue(USER_INFO, "")
            Gson().fromJson(userInfo, UserInfo::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getToken(): String {
        return getValue(TOKEN, "")
    }
}