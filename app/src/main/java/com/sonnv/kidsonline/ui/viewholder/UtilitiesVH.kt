package com.sonnv.kidsonline.ui.viewholder

import android.content.Context
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemFastUtilityLayoutBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.UtilityModel
import com.sonnv.kidsonline.ui.adapter.UtilityAdapter

open class UtilitiesVH(context: Context, itemViewBinding: ItemFastUtilityLayoutBinding) :
    BaseVH(itemViewBinding.root) {

    private val mContext = context
    private val binding = itemViewBinding
    private var isShow: Boolean = false
    private var isEditable: Boolean = true
    private var type: Int = 0
    private val mAdapter = UtilityAdapter(mContext)

    private var mData: List<UtilityModel> = arrayListOf(
        UtilityModel(R.drawable.ic_map, context.getString(R.string.label_donve)),
        UtilityModel(R.drawable.ic_leave, context.getString(R.string.label_leave)),
        UtilityModel(R.drawable.ic_scale, context.getString(R.string.label_index))
    )

    private var mUtilityCallback: ((UtilityModel) -> Unit?)? = null
    private var mEditClickCallback: (() -> Unit?)? = null

    fun setUtilityCallback(utilityCallback: ((UtilityModel) -> Unit?)?) {
        mUtilityCallback = utilityCallback
    }

    fun setEditClickCallback(editClickCallback: (() -> Unit?)? = null) {
        this.mEditClickCallback = editClickCallback
    }

    fun setShowDesc(isShow: Boolean) {
        this.isShow = isShow
    }

    fun setEditable(enable: Boolean) {
        this.isEditable = enable
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun setFullData() {
        mData = arrayListOf(
            UtilityModel(R.drawable.ic_conversation, mContext.getString(R.string.label_message)),
            UtilityModel(R.drawable.ic_medicine, mContext.getString(R.string.label_danthuoc)),
            UtilityModel(R.drawable.ic_activity_daily, mContext.getString(R.string.label_activity_daily)),
            UtilityModel(R.drawable.ic_news, mContext.getString(R.string.label_news)),
//            UtilityModel(R.drawable.ic_picture, mContext.getString(R.string.label_album_picture)),
//            UtilityModel(R.drawable.ic_money, mContext.getString(R.string.label_fee)),
//            UtilityModel(R.drawable.ic_copied, mContext.getString(R.string.label_survey)),
//            UtilityModel(R.drawable.ic_highlighter, mContext.getString(R.string.label_comment))
        )
    }


    fun bindData(position: Int) {
        binding.apply {
            tvDesc.isVisible = isShow
            tvEdit.isVisible = false
            tvLabel.text = when(type) {
                1 -> mContext.getString(R.string.label_feature_pinned)
                2 -> mContext.getString(R.string.label_feature_all)
                else -> mContext.getString(R.string.label_fast_utility)
            }

            rcv.layoutManager = GridLayoutManager(mContext, 3)
            rcv.adapter = mAdapter
            mAdapter.setUtilityCallback(mUtilityCallback)
            mAdapter.setData(mData)

            tvEdit.setOnClickListener {
                mEditClickCallback?.invoke()
            }
        }
    }
}