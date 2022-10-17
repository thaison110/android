package com.sonnv.kidsonline.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.ui.adapter.MainPagerAdapter
import com.sonnv.kidsonline.databinding.FragmentMainBinding
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MainViewModel

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {
//    private lateinit var mPagerAdapter: MainPagerAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(MainViewModel::class.java)
    }

    override fun initView() {
        initPagerAdapter()
    }

    override fun initObserve() {

    }

    override fun doWork() {

    }

    private fun initPagerAdapter() {
        mBinding.apply {
            vpg.isUserInputEnabled = false
            vpg.adapter = MainPagerAdapter(this@MainFragment)
            vpg.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        PageNumber.HOME_PAGE.ordinal -> bottomNavView.selectedItemId = R.id.action_home
                        PageNumber.FEATURE_PAGE.ordinal -> bottomNavView.selectedItemId = R.id.action_feature
                        PageNumber.NOTI_PAGE.ordinal -> bottomNavView.selectedItemId = R.id.action_noti
                        PageNumber.PROFILE_PAGE.ordinal -> bottomNavView.selectedItemId = R.id.action_me
                    }
                }
            })

            // for bottom bar
            bottomNavView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_home -> vpg.currentItem = PageNumber.HOME_PAGE.ordinal
                    R.id.action_feature -> vpg.currentItem = PageNumber.FEATURE_PAGE.ordinal
                    R.id.action_noti -> vpg.currentItem = PageNumber.NOTI_PAGE.ordinal
                    R.id.action_me -> vpg.currentItem = PageNumber.PROFILE_PAGE.ordinal
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    fun switchTab(position: Int) {
        mBinding.apply {
            vpg.currentItem = position
        }
    }

    enum class PageNumber(position: Int) {
        HOME_PAGE(0),
        FEATURE_PAGE(1),
        NOTI_PAGE(2),
        PROFILE_PAGE(3)
    }
}