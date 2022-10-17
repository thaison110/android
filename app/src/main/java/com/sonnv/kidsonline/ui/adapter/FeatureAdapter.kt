package com.sonnv.kidsonline.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemEmptyLayoutBinding
import com.sonnv.kidsonline.databinding.ItemFastUtilityLayoutBinding
import com.sonnv.kidsonline.model.FastUtilityModel
import com.sonnv.kidsonline.model.FeedType
import com.sonnv.kidsonline.model.FullUtilityModel
import com.sonnv.kidsonline.model.UtilityModel
import com.sonnv.kidsonline.ui.viewholder.EmptyVH
import com.sonnv.kidsonline.ui.viewholder.UtilitiesVH

class FeatureAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private val mData = arrayListOf(
        FastUtilityModel(),
        FullUtilityModel()
    )
    private var mUtilityCallback: ((UtilityModel) -> Unit?)? = null

    fun setUtilityCallback(utilityCallback: ((UtilityModel) -> Unit?)?) {
        mUtilityCallback = utilityCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FeedType.FAST_UTILITY -> {
                return UtilitiesVH(
                    mContext, ItemFastUtilityLayoutBinding.inflate(
                        LayoutInflater.from(mContext),
                        parent,
                        false
                    )
                )
            }

            FeedType.FULL_UTILITY -> {
                return UtilitiesVH(
                    mContext, ItemFastUtilityLayoutBinding.inflate(
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
        when (getItemViewType(position)) {
            FeedType.FAST_UTILITY -> {
                val fastUtilityVH = holder as UtilitiesVH
                fastUtilityVH.apply {
                    setShowDesc(true)
                    setType(1)
                    setUtilityCallback(mUtilityCallback)
                    setEditClickCallback {

                    }
                    bindData(position)
                }
            }

            FeedType.FULL_UTILITY -> {
                val fastUtilityVH = holder as UtilitiesVH
                fastUtilityVH.apply {
                    setFullData()
                    setEditable(false)
                    setShowDesc(false)
                    setType(2)
                    setUtilityCallback(mUtilityCallback)
                    bindData(position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].getFeedType()
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}