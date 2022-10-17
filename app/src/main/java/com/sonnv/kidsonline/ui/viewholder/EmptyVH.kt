package com.sonnv.kidsonline.ui.viewholder

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.sonnv.kidsonline.databinding.ItemEmptyLayoutBinding
import com.sonnv.kidsonline.databinding.ItemFastUtilityLayoutBinding
import com.sonnv.kidsonline.ui.adapter.UtilityAdapter

open class EmptyVH(context: Context, itemViewBinding: ItemEmptyLayoutBinding) :
    BaseVH(itemViewBinding.root) {

    private val mContext = context
    private val binding = itemViewBinding


    fun bindData(position: Int) {

    }
}