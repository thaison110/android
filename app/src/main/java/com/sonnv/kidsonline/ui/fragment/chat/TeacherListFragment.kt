package com.sonnv.kidsonline.ui.fragment.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentTeacherListBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.chat.ConversationAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_CONVERSATION_DATA
import com.sonnv.kidsonline.viewmodel.ChatViewModel

class TeacherListFragment: BaseFragment<ChatViewModel, FragmentTeacherListBinding>() {
    private lateinit var mAdapter: ConversationAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTeacherListBinding {
        return FragmentTeacherListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(ChatViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }

            searchView.edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    if(p0.isNullOrBlank()) {
//                        imgSend.setColorFilter(ContextCompat.getColor(context!!, R.color.color_D9D9D9), android.graphics.PorterDuff.Mode.SRC_IN)
//                    } else {
//                        imgSend.setColorFilter(ContextCompat.getColor(context!!, R.color.main_color), android.graphics.PorterDuff.Mode.SRC_IN)
//                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                mAdapter = ConversationAdapter(it)
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                rcv.adapter = mAdapter

                mAdapter.setConversationCallback { conversation ->
                    findNavController().navigateWithAnim(R.id.chatFragment,
                        bundleOf(PARAM_CONVERSATION_DATA to conversation)
                    )
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