package com.sonnv.kidsonline.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemEmptyLayoutBinding
import com.sonnv.kidsonline.databinding.ItemFastUtilityLayoutBinding
import com.sonnv.kidsonline.databinding.ItemHomeNewsFeedLayoutBinding
import com.sonnv.kidsonline.databinding.ItemUtilityLayoutBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.*
import com.sonnv.kidsonline.ui.viewholder.EmptyVH
import com.sonnv.kidsonline.ui.viewholder.UtilitiesVH
import com.sonnv.kidsonline.ui.viewholder.HomeNewsFeedVH

class NewsFeedAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context

    private val mData = arrayListOf(
        TodayActivityModel(),
//        NewsSchoolModel(),
//        NewsTeacherModel()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FeedType.TODAY_ACTIVITY -> {
                return UtilitiesVH(
                    mContext, ItemFastUtilityLayoutBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }

            FeedType.NEWS_SCHOOL -> {
                return HomeNewsFeedVH(
                    mContext, ItemHomeNewsFeedLayoutBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }

            FeedType.NEWS_TEACHER -> {
                return HomeNewsFeedVH(
                    mContext, ItemHomeNewsFeedLayoutBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }
        }
        return EmptyVH(
            mContext,
            ItemEmptyLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val utilityVH = holder as? UtilityVH
        utilityVH?.apply {
            bindData(position)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].getFeedType()
    }

    inner class UtilityVH(itemViewBinding: ItemUtilityLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        private val binding = itemViewBinding

        fun bindData(position: Int) {
            val utility = mData[position]
            binding.apply {
                root.setOnClickAffect {

                }
            }
        }
    }
}