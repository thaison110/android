package com.sonnv.kidsonline.ui.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentChatListBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.chat.ConversationAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_CONVERSATION_DATA
import com.sonnv.kidsonline.viewmodel.ChatViewModel

class ChatListFragment : BaseFragment<ChatViewModel, FragmentChatListBinding>() {
    private lateinit var mAdapter: ConversationAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentChatListBinding {
        return FragmentChatListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(ChatViewModel::class.java)
    }

    override fun initView() {
        mBinding.btnNewMess.setOnClickListener {
            findNavController().navigateWithAnim(R.id.searchTeacher)
        }
        mBinding.tvLabel.setOnClickAffect {
            activity?.onBackPressed()
        }
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                mAdapter = ConversationAdapter(it)
                rcv.adapter = mAdapter
                mAdapter.setConversationCallback { conversation ->
                    findNavController().navigateWithAnim(R.id.chatFragment,
                    bundleOf(PARAM_CONVERSATION_DATA to conversation))
                }
            }
        }
    }

    override fun initObserve() {
        mViewModel.conversationLiveData.observe(this) {
            mAdapter.setData(it ?: arrayListOf())
        }
    }

    override fun doWork() {
        mViewModel.getTeacherList()
    }
}