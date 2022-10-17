package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.DialogAbsenceLateBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplayNotify
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.convertTimestampToString4
import com.sonnv.kidsonline.extension.getDayOfWeek
import com.sonnv.kidsonline.model.AbsenceModel

class AbsenceLateDialog : BottomSheetDialogFragment() {
    private var mBinding: DialogAbsenceLateBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_absence_late, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val absenceModel = arguments?.getSerializable("absence_data") as? AbsenceModel

        mBinding?.apply {
            val hour = absenceModel?.createdAt?.convertTimeToDisplayNotify()
            val date = absenceModel?.createdAt?.convertTimestampToString2()
            val day = absenceModel?.createdAt?.convertTimestampToString4()?.getDayOfWeek()
            tvDay.text = day.plus(", Ngày ").plus(date)
            tvReason.text = getString(R.string.reasonDetail, absenceModel?.note)
            tvContent.text = getString(R.string.schoolConfirmed, hour, date)
        }
    }

    companion object {
        val TAG = AbsenceLateDialog.javaClass.simpleName

        fun newInstance(model: AbsenceModel): AbsenceLateDialog {
            val args = Bundle()
            args.putSerializable("absence_data", model)
            val fragment = AbsenceLateDialog()
            fragment.arguments = args
            return fragment
        }
    }
}