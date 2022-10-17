package com.sonnv.kidsonline.ui.adapter.feature.donve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemHommieLayoutBinding
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.HomieModel
import com.sonnv.kidsonline.util.getRelationShip

class HomieAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mData: MutableList<HomieModel> = arrayListOf()
    
    private var mHomieClickCallback: ((HomieModel) -> Unit) ?= null
    
    fun setClickCallback(homieClickCallback: ((HomieModel) -> Unit) ?= null) {
        mHomieClickCallback = homieClickCallback
    }

    fun setData(data: List<HomieModel>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomieVH(ItemHommieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val homieVH = holder as HomieVH
        homieVH.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class HomieVH(val binding: ItemHommieLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {
            val data = mData[position]

            binding.apply {
                root.setOnClickListener { mHomieClickCallback?.invoke(data) }
                imgAvatar.loadImage(data.image)
                tvUserName.text = data.name
                tvRelationship.text = getRelationShip(data.relationShip)
            }
        }

    }
}