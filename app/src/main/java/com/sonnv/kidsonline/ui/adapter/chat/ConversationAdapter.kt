package com.sonnv.kidsonline.ui.adapter.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemConversationLayoutBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplay
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.Conversation

class ConversationAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData: List<Conversation> = arrayListOf()

    fun setData(data: List<Conversation>) {
        mData = data
        notifyDataSetChanged()
    }

    private var conversationCallback: ((Conversation) -> Unit?)? = null

    fun setConversationCallback(conversationCallback: ((Conversation) -> Unit?)? = null) {
        this.conversationCallback = conversationCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ConversationVH(
            ItemConversationLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ConversationVH)?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ConversationVH(binding: ItemConversationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemConversationLayoutBinding = binding

        fun bindData(position: Int) {
            val conversation = mData[position]
            binding.apply {
                tvUserName.text = conversation.userName
                tvShortMess.text = conversation.shortText
                imgAvatar.loadImage(conversation.avatar)
                tvTime.text = conversation.time.convertTimeToDisplay()
//                root.setBackgroundResource(if (position == 0) R.drawable.bg_gray_boder_radius_12 else R.drawable.bg_gray_radius_12)
                root.setOnClickAffect {
                    conversationCallback?.invoke(conversation)
                }
            }
        }
    }
}