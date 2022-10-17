package com.sonnv.kidsonline.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.model.response.BaseResponse
import com.sonnv.kidsonline.ui.LoginActivity
import com.sonnv.kidsonline.ui.MainActivity
import com.sonnv.kidsonline.util.DayOfWeek
import com.sonnv.kidsonline.util.PrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


fun NavController.navigateWithAnim(
    idDestination: Int,
    bundle: Bundle? = null,
    popupToId: Int? = null
) {
    val anim = navOptions {
        anim {
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
        }
        popupToId?.let {
            popUpTo(popupToId) {
                inclusive = true
            }
        }
    }
    this.navigate(idDestination, bundle, anim)
}

fun <T : BaseResponse> Call<T>.enqueueCustom(
    context: Context,
    onResponse: (Response<T>) -> Unit,
    onFailure: () -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            (response.body() as? BaseResponse)?.let {
                if (it.status == 401) {
                    (context as? MainActivity)?.showMessage(context.getString(R.string.login_expired))
                    PrefUtils.getInstance(context).clearAll()
                    context.startActivity((Intent(context, LoginActivity::class.java)))
                    (context as? Activity)?.finish()
                    return@let
                }
            }
            onResponse.invoke(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure.invoke()
        }

    })
}

fun Activity?.hideKeyboard() {
    val currentFocusedView = this?.currentFocus
    if (currentFocusedView != null) {
        val inputManager: InputMethodManager =
            this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

//fun TextView.setAbsenceStatus(status: AbsenceStatus) {
//    when (status) {
//        AbsenceStatus.CONFIRMED -> {
//            setTextColor(ContextCompat.getColor(context, R.color.white))
//            background = ContextCompat.getDrawable(context, R.drawable.ic_absence_confirmed)
//        }
//        AbsenceStatus.NOT_CONFIRMED -> {
//            setTextColor(ContextCompat.getColor(context, R.color.white))
//            background = ContextCompat.getDrawable(context, R.drawable.ic_absence_not_confirm)
//        }
//        AbsenceStatus.PENDING -> {
//            setTextColor(ContextCompat.getColor(context, R.color.white))
//            background = ContextCompat.getDrawable(context, R.drawable.ic_absence_not_confirm)
//        }
//        AbsenceStatus.LATE -> {
//            setTextColor(ContextCompat.getColor(context, R.color.black))
//            background = ContextCompat.getDrawable(context, R.drawable.ic_absence_late)
//        }
//        else -> {
//        }
//    }
//
//}

fun View.hideKeyboardClickOutside(activity: Activity) {

    if (this !is EditText) {
        this.setOnTouchListener { v, event ->
            activity.hideKeyboard()
            false
        }
    }

    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            val innerView: View = this.getChildAt(i)
            innerView.hideKeyboardClickOutside(activity)
        }
    }
}

fun TextView.setTextColorStatus(isPaid: Boolean) {
    this.setTextColor(resources.getColor(if (isPaid) R.color.green_27AE60 else R.color.red_FF5959))
}

fun ViewGroup.doSomethingWhenClickOutside(action: () -> Unit) {
    for (i in 0 until childCount) {
        val innerView: View = getChildAt(i)
        innerView.setOnTouchListener { v, event ->
            action.invoke()
            false
        }
    }
}

fun String.convertTimeToDisplay(): String {
    try {
        val time = this.toLong() * 1000
        val currentTime = System.currentTimeMillis()
        val offsetTime = currentTime - time
        if (offsetTime < 60 * 1000) { // nhỏ hơn 1 phút
            return "${(offsetTime / 1000)}".plus(" giây trước")
        }
        if (offsetTime < 60 * 60 * 1000) { // nhỏ hơn 1 giờ
            return "${(offsetTime / 60000)}".plus(" phút trước")
        }
        if (offsetTime < 24 * 60 * 60 * 1000) { // nhỏ hơn 1 ngày
            return "${(offsetTime / (60 * 60 * 1000))}".plus(" giờ trước")
        }

        return convertTimestampToString(time)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return this
}

fun String.convertTimeToDisplayNotify(): String {
    val time = this.toLong() * 1000
    val sdf = SimpleDateFormat("hh:mm")
    val netDate = Date(time)
    return sdf.format(netDate)
}

private fun convertTimestampToString(time: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun String.convertTimestampToString2(): String {
    val time = this.toLong() * 1000
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun String.convertTimestampToString3(): String {
    val time = this.toLong() * 1000
    val sdf = SimpleDateFormat("hh:mm, dd/MM/yyyy")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun String.convertTimestampToString4(): String {
    val time = this.toLong() * 1000
    val sdf = SimpleDateFormat("EEE")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun String.getDayOfWeek():String {
    return when (this) {
        DayOfWeek.CN.dayOfWeekE -> DayOfWeek.CN.value
        DayOfWeek.T2.dayOfWeekE -> DayOfWeek.T2.value
        DayOfWeek.T3.dayOfWeekE -> DayOfWeek.T3.value
        DayOfWeek.T4.dayOfWeekE -> DayOfWeek.T4.value
        DayOfWeek.T5.dayOfWeekE -> DayOfWeek.T4.value
        DayOfWeek.T6.dayOfWeekE -> DayOfWeek.T6.value
        DayOfWeek.T7.dayOfWeekE -> DayOfWeek.T7.value
        else -> ""
    }
}

fun String.convertTimestampDDMM(): String {
    val time = this.toLong() * 1000
    val sdf = SimpleDateFormat("dd/MM")
    val netDate = Date(time)
    return sdf.format(netDate)
}

fun Int.getDayOfWeek(): String {
    return when (this) {
        DayOfWeek.CN.dayOfWeek -> DayOfWeek.CN.value
        DayOfWeek.T2.dayOfWeek -> DayOfWeek.T2.value
        DayOfWeek.T3.dayOfWeek -> DayOfWeek.T3.value
        DayOfWeek.T4.dayOfWeek -> DayOfWeek.T4.value
        DayOfWeek.T5.dayOfWeek -> DayOfWeek.T4.value
        DayOfWeek.T6.dayOfWeek -> DayOfWeek.T6.value
        DayOfWeek.T7.dayOfWeek -> DayOfWeek.T7.value
        else -> ""
    }
}

fun convertImageToBase64(path: String): String {
    val bm = BitmapFactory.decodeFile(path)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 10, baos) // bm is the bitmap object

    val b: ByteArray = baos.toByteArray()

    return "data:image/png;base64," + android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
}

fun ImageView.setShowPassword(edtPassword: EditText) {
    var isShow = false
    this.setOnClickAffect {
        isShow = !isShow
        this.setBackgroundResource(if (isShow) R.drawable.ic_eye_show else R.drawable.ic_eye_blind)
        showPass(edtPassword,isShow)
    }
}

fun showPass(edtPassword: EditText, show: Boolean) {
    edtPassword.transformationMethod =
        if (show) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    edtPassword.setSelection(edtPassword.text.length)
}
