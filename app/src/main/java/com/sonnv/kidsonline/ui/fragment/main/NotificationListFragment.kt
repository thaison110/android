package com.sonnv.kidsonline.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.FragmentNotificationBinding
import com.sonnv.kidsonline.ui.adapter.noti.NotificationAdapter
import com.sonnv.kidsonline.ui.dialog.NotificationDetailDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.NotificationViewModel

class NotificationListFragment :
    BaseFragment<NotificationViewModel, FragmentNotificationBinding>() {
    private lateinit var mAdapter: NotificationAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(NotificationViewModel::class.java)
    }

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                mAdapter = NotificationAdapter(it)
                rcv.adapter = mAdapter
                mAdapter.setConversationCallback { noti ->
                    NotificationDetailDialog.newInstance(noti)
                        .show(childFragmentManager, NotificationDetailDialog.TAG)
                }
            }
        }
    }

    override fun initObserve() {
        mViewModel.listNotifyLiveData.observe(this) {
            it?.let {
                mAdapter.setList(it)
            }
        }

    }

    override fun doWork() {
        mViewModel.getListNotify()
    }

    companion object {
        fun newInstance(): NotificationListFragment {
            return NotificationListFragment()
        }
    }
}