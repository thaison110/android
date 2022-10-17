package com.sonnv.kidsonline.ui.adapter.noti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemNotiLayoutBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplayNotify
import com.sonnv.kidsonline.extension.convertTimestampToString3
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.NotificationModel

class NotificationAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData: List<NotificationModel> = arrayListOf()

    private var notificationCallback: ((NotificationModel) -> Unit?)? = null

    fun setList(mList: List<NotificationModel>?) {
        mList?.let {
            mData = mList
            notifyDataSetChanged()
        }
    }

    fun setConversationCallback(notificationCallback: ((NotificationModel) -> Unit?)? = null) {
        this.notificationCallback = notificationCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationVH(
            ItemNotiLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NotificationVH)?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class NotificationVH(binding: ItemNotiLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemNotiLayoutBinding = binding

        fun bindData(position: Int) {
            val conversation = mData[position]
            binding.apply {
                tvUserName.text = conversation.senderName
                tvShortMess.text = conversation.title
                tvTime.text = conversation.createdAt.toString().convertTimestampToString3()
                //conversation.status == 0 -> chưa đọc?
                root.setBackgroundResource(if (conversation.status == 0) R.drawable.bg_gray_boder_radius_12 else R.drawable.bg_gray_radius_12)
                root.setOnClickAffect {
                    notificationCallback?.invoke(conversation)
                }
            }
        }
    }
}