package com.sonnv.kidsonline.ui.fragment.feature.donve.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.FragmentDonveHistoryListBinding
import com.sonnv.kidsonline.ui.adapter.feature.donve.dondon.CalendarDonAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class HistoryDonFragment : BaseFragment<DonveViewModel, FragmentDonveHistoryListBinding>() {

    private val mAdapter: CalendarDonAdapter = CalendarDonAdapter()

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryDonFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDonveHistoryListBinding {
        return FragmentDonveHistoryListBinding.inflate(LayoutInflater.from(context))
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            rcv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rcv.adapter = mAdapter
        }
    }

    override fun initObserve() {
        mViewModel.donveHistoryLiveData.observe(this) {
            it?.let {
                mAdapter.setData(it)
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHistory(it)
        }
    }
}