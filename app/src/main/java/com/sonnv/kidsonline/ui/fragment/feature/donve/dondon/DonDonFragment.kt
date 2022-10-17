package com.sonnv.kidsonline.ui.fragment.feature.donve.dondon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentDonDonBinding
import com.sonnv.kidsonline.extension.loadImageDrawable
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.feature.donve.dondon.CalendarDonAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.BaseViewModel
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class DonDonFragment : BaseFragment<DonveViewModel, FragmentDonDonBinding>() {

    private lateinit var calendarDonAdapter: CalendarDonAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            DonDonFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDonDonBinding {
        return FragmentDonDonBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        calendarDonAdapter = CalendarDonAdapter()
        initDefaultView()
        initListener()
    }

    private fun initListener() {
        mBinding.apply {
            cardDonDung.root.setOnClickListener {
                goToCreateDonDonFragment(0)
            }
            cardDonSom.root.setOnClickListener {
                goToCreateDonDonFragment(1)
            }
            cardDonMuon.root.setOnClickListener {
                goToCreateDonDonFragment(2)
            }
        }
    }

    private fun goToCreateDonDonFragment(type: Int) {
        findNavController().navigateWithAnim(
            R.id.createDonDonFragment,
            bundleOf("type" to type)
        )
    }

    private fun initDefaultView() {
        mBinding.apply {
            cardDonSom.apply {
                imv.loadImageDrawable(R.drawable.ic_clock_don_som)
                tvDon.text = "Đón sớm"
                context?.let {
                    tvDon.setTextColor(ContextCompat.getColor(it, R.color.color_FF7917))
                    container.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it,
                            R.color.color_FFF9E3
                        )
                    )
                }
            }

            cardDonMuon.apply {
                imv.loadImageDrawable(R.drawable.ic_clock_don_muon)
                tvDon.text = "Đón muộn"
                context?.let {
                    tvDon.setTextColor(ContextCompat.getColor(it, R.color.color_EB5757))
                    container.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it,
                            R.color.color_FFEDEC
                        )
                    )
                }
            }

            rcvCalendar.adapter = calendarDonAdapter
        }
    }

    override fun initObserve() {
        mViewModel.donveHistoryLiveData.observe(this) {
            it?.let {
                calendarDonAdapter.setData(it)
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHistory(it)
        }
    }
}