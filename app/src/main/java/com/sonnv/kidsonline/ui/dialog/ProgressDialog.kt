package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sonnv.kidsonline.databinding.DialogProgressBinding

class ProgressDialog : DialogFragment() {
    private var binding: DialogProgressBinding? = null
    private var isShown: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setupDialog()
        binding = DialogProgressBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun setupDialog() {
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        isCancelable = false
    }

    fun show(manager: FragmentManager) {
        if (isShown) return
        isShown = true
        show(manager, null)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction()
            .add(this, tag)
            .commitAllowingStateLoss()
    }

    override fun dismiss() {
        isShown = false
        dismissAllowingStateLoss()
    }
}
