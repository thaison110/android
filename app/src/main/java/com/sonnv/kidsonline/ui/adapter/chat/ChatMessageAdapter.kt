package com.sonnv.kidsonline.ui.adapter.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemChatMessageBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplay
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.ChatMessage
import com.sonnv.kidsonline.util.PrefUtils

class ChatMessageAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData: List<ChatMessage> = arrayListOf()

    fun setData(data: List<ChatMessage>) {
        mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatMessageVH(
            ItemChatMessageBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ChatMessageVH)?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ChatMessageVH(binding: ItemChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemChatMessageBinding = binding

        fun bindData(position: Int) {
            val chatMessage = mData[position]
            binding.apply {
                tvMess.text = chatMessage.content
                tvTime.text = chatMessage.time.convertTimeToDisplay()
                messageView.setBackgroundResource(if (isMe(chatMessage.senderId)) R.drawable.bg_out_going else R.drawable.bg_in_comming)

                var layoutParram = messageView.layoutParams as ConstraintLayout.LayoutParams
                if (chatMessage.type == 1) {
                    layoutParram.startToStart = root.id
                    layoutParram.endToEnd = -1
                } else {
                    layoutParram.endToEnd = root.id
                    layoutParram.startToStart = -1
                }
                messageView.requestLayout()
            }
        }
    }

    fun isMe(id: Int): Boolean {
        val userInfoMe = PrefUtils.getInstance().getUserInfo()
        return userInfoMe?.id == id
    }
}