package com.sonnv.kidsonline.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.DialogConfirmBinding
import com.sonnv.kidsonline.extension.setOnClickAffect

class ConfirmDialog() : FullScreenDialog() {
    private var mBinding: DialogConfirmBinding? = null
    var deleteCallback: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = R.style.MyAnimation
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogConfirmBinding.inflate(LayoutInflater.from(context))
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewListener()
    }

    private fun initView() {
        mBinding?.apply {

        }
    }

    private fun initViewListener() {
        mBinding?.apply {
            tvCancel.setOnClickAffect {
                dismiss()
            }
            tvDelete.setOnClickAffect {
                deleteCallback?.invoke()
                dismiss()
            }
        }
    }

    fun setCallback(deleteCallback: (() -> Unit)? = null) {
        this.deleteCallback = deleteCallback
    }

    companion object {
        fun newInstance(): ConfirmDialog {
            return ConfirmDialog()
        }
    }
}