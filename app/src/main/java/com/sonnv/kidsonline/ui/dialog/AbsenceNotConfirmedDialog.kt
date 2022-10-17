package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.DialogAbsenceNotConfirmBinding
import com.sonnv.kidsonline.extension.setOnClickAffect

class AbsenceNotConfirmedDialog(
    val date: String,
    val isPending: Boolean = false,
    val callback: ((date: String) -> Unit)
) : BottomSheetDialogFragment() {
    private var mBinding: DialogAbsenceNotConfirmBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogAbsenceNotConfirmBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            tvDay.text = date
            btConfirm.isVisible = !isPending
            btPending.isVisible = isPending
            tvContent.text =
                if (isPending) "Chờ nhà trường xác nhận" else "Nhà trường chưa xác nhận"
            btCancel.setOnClickAffect { dismiss() }
            btConfirm.setOnClickAffect {
                dismiss()
                callback.invoke(date)
            }
        }
    }
}