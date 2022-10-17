package com.sonnv.kidsonline.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class FullScreenDialog: DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let { Dialog(it) }
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog?: super.onCreateDialog(savedInstanceState)
    }
}