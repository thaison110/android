package com.sonnv.kidsonline.ui.viewholder

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemHomeNewsFeedLayoutBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.UtilityAdapter

open class HomeNewsFeedVH(context: Context, itemViewBinding: ItemHomeNewsFeedLayoutBinding) :
    BaseVH(itemViewBinding.root) {

    private val mContext = context
    private val binding = itemViewBinding


    fun bindData(position: Int) {
        binding.apply {
            rcv.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
            rcv.adapter = UtilityAdapter(mContext)

            tvEdit.setOnClickAffect {

            }
        }
    }
}