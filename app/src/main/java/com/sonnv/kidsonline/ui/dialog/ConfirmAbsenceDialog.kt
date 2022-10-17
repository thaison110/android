package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.DialogAbsenceConfirmBinding
import com.sonnv.kidsonline.extension.setOnClickAffect

class ConfirmAbsenceDialog(val date: String, val callback: ((String) -> Unit)) :
    BottomSheetDialogFragment() {
    private var mBinding: DialogAbsenceConfirmBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_absence_confirm, container, false)

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            tvDay.text = date
            btCancel.setOnClickAffect { dismiss() }
            btConfirm.setOnClickAffect {
                dismiss()
                callback.invoke(date) }
        }
    }
}