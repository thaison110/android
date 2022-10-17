package com.sonnv.kidsonline.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemCameraBinding
import com.sonnv.kidsonline.databinding.ItemGalleryBinding
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.ImageModel

class GalleryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mData: MutableList<ImageModel> = arrayListOf()

    private var callback: ((Int, ImageModel?) -> Unit)? = null

    private var callbackCount: ((Int) -> Unit)? = null

    private var isSingleChoice = false

    fun setClickItemCallback(callback: ((Int, ImageModel?) -> Unit)? = null) {
        this.callback = callback
    }

    fun setCountCallback(callback: ((Int) -> Unit)? = null) {
        this.callbackCount = callback
    }

    fun setSingleChoice(isSingleChoice: Boolean) {
        this.isSingleChoice = isSingleChoice
    }

    fun getImageSelected(): List<ImageModel> = mData.filter { it.isSelected }

    fun setData(data: MutableList<ImageModel>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CAMERA_TYPE -> {
                CameraVH(
                    ItemCameraBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ImageVH(
                    ItemGalleryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CAMERA_TYPE -> {
                val cameraVH = holder as? CameraVH
                cameraVH?.bindData(position)
            }

            else -> {
                val imageVH = holder as? ImageVH
                imageVH?.bindData(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return IMAGE_TYPE
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class CameraVH(binding: ItemCameraBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mBinding = binding
        fun bindData(position: Int) {
            mBinding.root.setOnClickAffect {
                callback?.invoke(CAMERA_TYPE, null)
            }
        }

    }

    private var currentPosition = -1
    inner class ImageVH(binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mBinding = binding
        private var selectedCount = 0

        fun bindData(position: Int) {
            val imageModel = mData[position]
            mBinding.apply {
                img.loadImage(imageModel.getPathFromUri(mBinding.root.context) ?: "")
                tvCount.isSelected = imageModel.isSelected

                root.setOnClickListener {
                    if (isSingleChoice) {
                        imageModel.isSelected = true
                    } else {
                        imageModel.isSelected = imageModel.isSelected.not()
                    }
                    notifyItemChanged(position)

                    if (isSingleChoice) {
                        if (currentPosition != -1) {
                            mData[currentPosition].isSelected = false
                            notifyItemChanged(currentPosition)
                        }
                        currentPosition = position
                        return@setOnClickListener
                    }

                    selectedCount = 0
                    mData.forEachIndexed { index, imageModel ->
                        if (imageModel.isSelected) {
                            selectedCount++
                        }
                    }

                    callbackCount?.invoke(selectedCount)
                }
            }
        }

    }

    companion object {
        const val CAMERA_TYPE = 1
        const val IMAGE_TYPE = 2
    }
}