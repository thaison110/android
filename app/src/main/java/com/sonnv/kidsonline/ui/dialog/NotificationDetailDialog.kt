package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.databinding.DialogNotiDetailBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplayNotify
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.NotificationModel

class NotificationDetailDialog : BottomSheetDialogFragment() {

    private var mBinding: DialogNotiDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogNotiDetailBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationModel = arguments?.getSerializable("noti_data") as? NotificationModel

        notificationModel?.let {
//            mBinding?.imgAvatar?.loadImageDrawable(it.avatar)
            mBinding?.apply {
                tvName.text = it.senderName
                tvDatetime.text = "Gửi vào ${it.createdAt.toString().convertTimeToDisplayNotify()}, ngày ${it.date.toString().convertTimestampToString2()}"
                tvTitle.text = it.title
                if (it.detail.isNotEmpty()) {
                    tvDetail.text = it.detail
                    tvDetail1.isVisible = false
                    tvDetail2.isVisible = false
                }
            }

        }
        mBinding?.btCancel?.setOnClickAffect {
            dismiss()
        }
    }

    companion object {
        val TAG = NotificationDetailDialog.javaClass.simpleName

        fun newInstance(notificationModel: NotificationModel): NotificationDetailDialog {
            val args = Bundle()
            args.putSerializable("noti_data", notificationModel)

            val fragment = NotificationDetailDialog()
            fragment.arguments = args
            return fragment
        }
    }
}