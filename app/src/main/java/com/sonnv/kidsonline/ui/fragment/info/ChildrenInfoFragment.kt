package com.sonnv.kidsonline.ui.fragment.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import com.sonnv.kidsonline.databinding.FragmentIndexInfoBinding
import com.sonnv.kidsonline.extension.*
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PrefUtils
import com.sonnv.kidsonline.viewmodel.AccountViewModel
import com.sonnv.kidsonline.viewmodel.MainViewModel
import java.util.*

class ChildrenInfoFragment : BaseFragment<AccountViewModel, FragmentIndexInfoBinding>() {

    private val yearData: MutableList<String> = arrayListOf()
    private var mCurrentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentIndexInfoBinding = FragmentIndexInfoBinding.inflate(inflater, container, false)

    override fun initViewModel() {
        mViewModel = getViewModel(AccountViewModel::class.java)
    }

    override fun initView() {
        initSelectionYear()
        mBinding.apply {
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }

            root.doSomethingWhenClickOutside {
                cardSelectionYear.isVisible = false
            }
            btnUpload.setOnClickAffect {  }
            btnDownload.setOnClickAffect {  }
            containerYear.forEachIndexed {index, view ->
                view.setOnClickListener {
                    tvFilter.text = (view as? TextView)?.text ?: mCurrentYear.toString()
                    mCurrentYear = yearData[index].toInt()

                    context?.let {
                        showProgress()
                        mViewModel.getHealthInfo(it, mCurrentYear)
                    }

                    cardSelectionYear.isVisible = false
                }
            }
            tvFilter.setOnClickListener {
                cardSelectionYear.isVisible = true
            }
            tvFilter.text = "Năm $mCurrentYear"
        }
    }

    private fun initSelectionYear() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in currentYear downTo  2000) {
            yearData.add(i.toString())
        }

        mBinding.apply {
            containerYear.addYear(context, yearData)
        }
    }

    override fun initObserve() {
        mViewModel.healthInfoLiveData.observe(this) {
            hideProgress()

            mBinding.viewChart.addHealthIndex(context, it)

            it?.lastOrNull()?.let {
                mBinding.apply {
                    tvHeight.text = it.height.toString()
                    tvWeight.text = it.weight.toString()
                }
            }
            if (it?.isEmpty() == true) {
                mBinding.apply {
                    tvHeight.text = "--"
                    tvWeight.text = "--"
                }
            }

            if (it == null) {
                showMessage("Có lỗi xảy ra!")
            }
        }
    }

    override fun doWork() {
        context?.let {
            val userInfo = PrefUtils.getInstance(it).getUserInfo()
            userInfo?.let {
                bindUserInfo(userInfo)
            }

            mViewModel.getHealthInfo(it, mCurrentYear)
        }
    }

    private fun bindUserInfo(userInfo: UserInfo) {
        mBinding.apply {
            imgAvatar.loadImage(userInfo.avatar)
            tvKidName.text = userInfo.fullname
            tvBirthday.text = "Ngày sinh: ${userInfo.birthday}"
        }
    }

    companion object {
        fun newInstance(): ChildrenInfoFragment {
            val args = Bundle()

            val fragment = ChildrenInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}