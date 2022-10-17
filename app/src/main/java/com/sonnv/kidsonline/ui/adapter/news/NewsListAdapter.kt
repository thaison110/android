package com.sonnv.kidsonline.ui.adapter.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemSchoolNewNotiLayoutBinding
import com.sonnv.kidsonline.databinding.ItemTeacherNewNotiLayoutBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplay
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.loadImageDrawable
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.NewsModel

class NewsListAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData: List<NewsModel> = arrayListOf()

    private var newClickCallback: ((NewsModel) -> Unit)? = null

    fun setNewsCallback(newClickCallback: ((NewsModel) -> Unit)? = null) {
        this.newClickCallback = newClickCallback
    }

    fun setData(it: List<NewsModel>?) {
        it?.let {
            mData = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SchoolNewsVH(
            ItemSchoolNewNotiLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SchoolNewsVH).bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class SchoolNewsVH(binding: ItemSchoolNewNotiLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val binding: ItemSchoolNewNotiLayoutBinding = binding

        fun bindData(position: Int) {
            val news = mData[position]
            binding.apply {
                root.setOnClickAffect {
                    newClickCallback?.invoke(news)
                }
                imgBall.loadImageDrawable(if (news.type == 0) R.drawable.ic_user else R.drawable.ic_graduate)
                imgThumb.loadImage(news.image)
                tvImportant.isVisible = news.isImportant == 1
                tvTitle.text = news.name
                tvTime.text = news.time.toString().convertTimeToDisplay()
            }
        }
    }

    inner class TeacherNewsVH(binding: ItemTeacherNewNotiLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val binding: ItemTeacherNewNotiLayoutBinding = binding

        fun bindData(position: Int) {
            val news = mData[position]
            binding.apply {
                root.setOnClickAffect {
                    newClickCallback?.invoke(news)
                }
            }
        }
    }
}