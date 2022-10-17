package com.sonnv.kidsonline.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemUtilityLayoutBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.extension.loadImageDrawable
import com.sonnv.kidsonline.model.UtilityModel

class UtilityAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context

    private var mData: List<UtilityModel> = arrayListOf()

    private var mUtilityCallback: ((UtilityModel) -> Unit?)? = null

    fun setUtilityCallback(utilityCallback: ((UtilityModel) -> Unit?)?) {
        mUtilityCallback = utilityCallback
    }

    fun setData(data: List<UtilityModel>) {
        mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemUtilityLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return UtilityVH(binding)
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

    inner class UtilityVH(itemViewBinding: ItemUtilityLayoutBinding): RecyclerView.ViewHolder(itemViewBinding.root) {
        private val binding = itemViewBinding

        fun bindData(position: Int) {
            val utility = mData[position]
            binding.apply {
                root.setOnClickAffect {
                    mUtilityCallback?.invoke(utility)
                }

                imgIcon.loadImageDrawable(utility.icon)
                tvLabel.text = utility.label
            }
        }
    }
}