package com.sonnv.kidsonline.ui.fragment.feature.donve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sonnv.kidsonline.databinding.FragmentDonVeBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.feature.donve.DonVePagerAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.BaseViewModel

class DonVeFragment : BaseFragment<BaseViewModel, FragmentDonVeBinding>() {
    private lateinit var pagerAdapter: DonVePagerAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDonVeBinding {
        return FragmentDonVeBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
    }

    override fun initView() {
        mBinding.tvDonDon.isSelected = true
        initAdapter()
        initListenner()
    }

    private fun initListenner() {
        mBinding.apply {
            tvDonVeHeader.setOnClickAffect {
                activity?.onBackPressed()
            }
            tvDonDon.setOnClickListener {
                it.isSelected = true
                tvNguoiDon.isSelected = false
                tvHistory.isSelected = false

                vpg.setCurrentItem(0, true)
            }

            tvNguoiDon.setOnClickListener {
                it.isSelected = true
                tvDonDon.isSelected = false
                tvHistory.isSelected = false

                vpg.setCurrentItem(1, true)
            }

            tvHistory.setOnClickListener {
                it.isSelected = true
                tvNguoiDon.isSelected = false
                tvDonDon.isSelected = false

                vpg.setCurrentItem(2, true)
            }
        }
    }

    private fun initAdapter() {
        mBinding.apply {
            pagerAdapter = DonVePagerAdapter(this@DonVeFragment)
            vpg.adapter = pagerAdapter
            vpg.isUserInputEnabled = false
        }
    }

    override fun initObserve() {
    }

    override fun doWork() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DonVeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}