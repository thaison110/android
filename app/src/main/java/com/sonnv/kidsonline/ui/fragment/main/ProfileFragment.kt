package com.sonnv.kidsonline.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentProfileBinding
import com.sonnv.kidsonline.ui.adapter.ProfilePagerAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.AccountViewModel
import com.sonnv.kidsonline.viewmodel.HomeViewModel
import com.sonnv.kidsonline.viewmodel.MainViewModel

class ProfileFragment : BaseFragment<AccountViewModel, FragmentProfileBinding>() {
    lateinit var homeViewModel: HomeViewModel

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(AccountViewModel::class.java)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun initView() {
        initPagerAdapter()

        mBinding.apply {
            tvChildrenInfo.setOnClickListener {
                setViewAfterClick(true)
                vpInfo.currentItem = CHILDREN_PAGE
            }
            tvParentInfo.setOnClickListener {
                setViewAfterClick(false)
                vpInfo.currentItem = PARENT_PAGE
            }
        }
    }

    private fun initPagerAdapter() {
        mBinding.apply {
            vpInfo.isUserInputEnabled = false
            vpInfo.adapter = ProfilePagerAdapter(this@ProfileFragment)
        }
    }

    override fun initObserve() {
    }

    override fun doWork() {
        homeViewModel.getUserInfo()
    }

    private fun setViewAfterClick(isClickChildren: Boolean) {
        mBinding.apply {
            tvChildrenInfo.setBackgroundResource(if (isClickChildren) R.drawable.bg_tab_orange_boder_radius_40 else R.drawable.bg_tab_boder_radius_40)
            tvParentInfo.setBackgroundResource(if (isClickChildren) R.drawable.bg_tab_boder_radius_40 else R.drawable.bg_tab_orange_boder_radius_40)
            tvChildrenInfo.setTextColor(
                if (isClickChildren) resources.getColor(R.color.white) else resources.getColor(
                    R.color.black
                )
            )
            tvParentInfo.setTextColor(
                if (isClickChildren) resources.getColor(R.color.black) else resources.getColor(
                    R.color.white
                )
            )
        }
    }

    companion object {
        const val CHILDREN_PAGE = 0
        const val PARENT_PAGE = 1

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}