package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.DialogAbsenceConfirmedBinding

class AbsenceConfirmedDialog : BottomSheetDialogFragment() {
    private var mBinding: DialogAbsenceConfirmedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogAbsenceConfirmedBinding.inflate(inflater, container, false)
        return mBinding?.root
    }
}