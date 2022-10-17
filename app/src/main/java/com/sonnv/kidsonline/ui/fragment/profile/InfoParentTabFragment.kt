package com.sonnv.kidsonline.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentInfoParentTabBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setDrawableLeft
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.ui.LoginActivity
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.ui.fragment.main.ProfileFragment
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.viewmodel.AccountViewModel

class InfoParentTabFragment : BaseFragment<AccountViewModel, FragmentInfoParentTabBinding>() {
    private var mIsEdit: Boolean = false
    private var userInfoModelOrg: UserInfo? = null
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentInfoParentTabBinding {
        return FragmentInfoParentTabBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(AccountViewModel::class.java)
    }

    private fun editView() {
        mBinding.apply {
            tvEdit.isVisible = !mIsEdit
            imgEditInfo.isVisible = !mIsEdit
            tvChangePassword.isVisible = !mIsEdit
            tvBiometric.isVisible = !mIsEdit
            tvLogOut.isVisible = !mIsEdit
            btnUpdateInfo.isVisible = mIsEdit
            itemPhone.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemEmail.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemAddress.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemUnionParent.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemUserParent.tvFeildValue.isFocusableInTouchMode = mIsEdit
        }
    }

    private fun setClearForcus() {
        mBinding.apply {
            itemPhone.tvFeildValue.clearFocus()
            itemEmail.tvFeildValue.clearFocus()
            itemAddress.tvFeildValue.clearFocus()
            itemUnionParent.tvFeildValue.clearFocus()
            itemUserParent.tvFeildValue.clearFocus()
        }
    }

    override fun initView() {
        mBinding.apply {
            tvLogOut.setOnClickAffect {
                PrefUtils.getInstance().clearAll()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
            imgEditInfo.setOnClickListener {
                mIsEdit = !mIsEdit
                editView()
            }

            btnUpdateInfo.setOnClickAffect {
                setClearForcus()
                mIsEdit = !mIsEdit
                editView()
                context?.let { ct ->
                    mViewModel.changeParentInfo(
                        ct,
                        itemUserParent.tvFeildValue.text.toString(),
                        itemPhone.tvFeildValue.text.toString(),
                        itemUnionParent.tvFeildValue.text.toString(),
                        itemEmail.tvFeildValue.text.toString(),
                        itemAddress.tvFeildValue.text.toString()
                    )
                }
            }

            tvChangePassword.setOnClickAffect {
                findNavController().navigateWithAnim(R.id.changePassword)
            }
        }
    }

    private fun setDataItem(userInfo: UserInfo) {
        mBinding.apply {
            itemUserParent.tvFeildLabel.text = getString(R.string.parentNameLabel)
            itemUserParent.tvFeildLabel.setDrawableLeft(R.drawable.ic_user)
            itemUserParent.tvFeildValue.setText(userInfo.parentName.ifEmpty { "Chưa có" })

            itemUnionParent.tvFeildLabel.text = getString(R.string.dateOfBirthLabel)
            itemUnionParent.tvFeildLabel.setDrawableLeft(R.drawable.ic_union)
            itemUnionParent.tvFeildValue.setText(userInfo.parentBirthDay.ifEmpty { "Chưa có" })

            itemAddress.tvFeildLabel.text = getString(R.string.addressLabel)
            itemAddress.tvFeildLabel.setDrawableLeft(R.drawable.ic_union)
            itemAddress.tvFeildValue.setText(userInfo.addess)

            itemEmail.tvFeildLabel.text = getString(R.string.emailLabel)
            itemEmail.tvFeildLabel.setDrawableLeft(R.drawable.ic_email_gray)
            itemEmail.tvFeildValue.setText(userInfo.email)

            itemPhone.tvFeildLabel.text = getString(R.string.phoneLabel)
            itemPhone.tvFeildLabel.setDrawableLeft(R.drawable.ic_phone_call)
            itemPhone.tvFeildValue.setText(userInfo.telePhone)
        }
    }

    override fun initObserve() {
        (parentFragment as? ProfileFragment)?.homeViewModel?.userInfoLiveData?.observe(this) {
            it?.let {
                setDataItem(it)
                userInfoModelOrg = it
            }
        }

        mViewModel.parentInfoLiveData.observe(this) { userModel ->
            if (mViewModel.parentInfoLiveData.value != null) {
                context?.let {
                    Toast.makeText(it, "Thông tin đã được cập nhật!", Toast.LENGTH_SHORT).show()
                    setDataItem(userModel)
                }
            } else {
                context?.let {
                    Toast.makeText(it, "Thông tin thay đổi không thành công!", Toast.LENGTH_SHORT)
                        .show()
                    userInfoModelOrg?.let { it1 -> setDataItem(it1) }
                }
            }
        }
    }

    override fun doWork() {
    }

    companion object {
        fun newInstance(): InfoParentTabFragment {
            return InfoParentTabFragment()
        }
    }
}