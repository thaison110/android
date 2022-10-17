package com.sonnv.kidsonline.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.databinding.DialogContactSupportBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.MainActivity

class ContactSupportDialog : BottomSheetDialogFragment() {

    private var mBinding: DialogContactSupportBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogContactSupportBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            btCancel.setOnClickAffect {
                dismiss()
            }
            tvCallSchool.setOnClickAffect {
                callPhone("19001509")
            }
            tvCallApp.setOnClickAffect {
                callPhone("19005657")
            }
            tvSendEmail.setOnClickAffect {

            }
        }
    }

    private fun callPhone(phoneNumber: String) {
        (activity as? MainActivity)?.callPhone(phoneNumber)
    }

    companion object {
        val TAG = ContactSupportDialog.javaClass.simpleName

        fun newInstance(): ContactSupportDialog {
            val args = Bundle()
            val fragment = ContactSupportDialog()
            fragment.arguments = args
            return fragment
        }
    }
}