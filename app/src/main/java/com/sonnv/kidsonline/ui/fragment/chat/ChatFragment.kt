package com.sonnv.kidsonline.ui.fragment.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentChatBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.Conversation
import com.sonnv.kidsonline.ui.adapter.chat.ChatMessageAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_CONVERSATION_DATA
import com.sonnv.kidsonline.viewmodel.ChatViewModel

class ChatFragment: BaseFragment<ChatViewModel, FragmentChatBinding>() {
    private val mConversation by lazy {arguments?.getSerializable(PARAM_CONVERSATION_DATA)  as? Conversation}
    private lateinit var mAdapter: ChatMessageAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentChatBinding {
        return FragmentChatBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(ChatViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }

            mConversation?.let { conversation ->
                tvUserName.text = conversation.userName
            }

            edtMess.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(p0.isNullOrBlank()) {
                        imgSend.setColorFilter(ContextCompat.getColor(context!!, R.color.color_D9D9D9), android.graphics.PorterDuff.Mode.SRC_IN)
                        imgSend.isEnabled = false
                    } else {
                        imgSend.setColorFilter(ContextCompat.getColor(context!!, R.color.main_color), android.graphics.PorterDuff.Mode.SRC_IN)
                        imgSend.isEnabled = true
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            imgSend.setOnClickListener {
                val message = edtMess.text.toString()
                mConversation?.let {
                    mViewModel.sendMessage(message, it.id)
                    edtMess.text.clear()
                }
            }
        }
        initAdapter()
    }

    private fun dpToPx(valueInDp: Float): Float {
        val metrics = context?.resources?.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                mAdapter = ChatMessageAdapter(it)
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, true)
                rcv.adapter = mAdapter
            }
        }
    }

    override fun initObserve() {
        mViewModel.conversationDetailLiveData.observe(this) {
            if (it.isNotEmpty()) {
                mAdapter.setData(it)
                mBinding.rcv.scrollToPosition(0)
            }
        }
    }

    override fun doWork() {
        mConversation?.let {
            mViewModel.getChatHistory(it.id)
        }
    }
}