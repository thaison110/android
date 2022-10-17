package com.sonnv.kidsonline.ui.fragment.profile

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentChangePasswordBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.extension.setShowPassword
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.AccountViewModel

class ChangePasswordFragment : BaseFragment<AccountViewModel, FragmentChangePasswordBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(AccountViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            btnUpdatePass.setOnClickAffect {
                context?.let { ct ->
                    mViewModel.changePassword(
                        ct,
                        edtPassOld.edtPass.text.toString(),
                        edtPassNew.edtPass.text.toString(),
                        edtPassConfirmNew.edtPass.text.toString()
                    )
                }
            }
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
            edtPassOld.btnEyes.setShowPassword(edtPassOld.edtPass)
            edtPassNew.btnEyes.setShowPassword(edtPassNew.edtPass)
            edtPassConfirmNew.btnEyes.setShowPassword(edtPassConfirmNew.edtPass)
        }
    }

    override fun initObserve() {
        mViewModel.changePasswordLiveData.observe(this) {
            if (mViewModel.changePasswordLiveData.value == true) {
                context?.let {
                    Toast.makeText(
                        it,
                        "Mật khẩu của bạn đã được thay đổi thành công!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                activity?.onBackPressed()
            } else {
                context?.let {
                    Toast.makeText(it, "Thay đổi mật khẩu không thành công!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun doWork() {
    }
}