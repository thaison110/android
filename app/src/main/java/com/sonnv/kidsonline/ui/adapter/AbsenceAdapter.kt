package com.sonnv.kidsonline.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemAbsenceBinding
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.AbsenceModel

class AbsenceAdapter : RecyclerView.Adapter<AbsenceAdapter.AbsenceVH>() {
    private var listData: List<AbsenceModel> = arrayListOf()
    var callback: ((AbsenceModel) -> Unit)? = null
    private var callbackClickItem: ((AbsenceModel) -> Unit)? = null

    fun setListData(list: List<AbsenceModel>) {
        listData = list
        notifyDataSetChanged()
    }

    fun setCallBackClick(mCallback: ((AbsenceModel) -> Unit)? = null) {
        this.callbackClickItem = mCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenceVH {
        return AbsenceVH(
            ItemAbsenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AbsenceVH, position: Int) {
        holder.bindData(listData[position])
        holder.mBinding.btConfirm.setOnClickAffect {
            callback?.invoke(listData[position])
        }
        holder.mBinding.root.setOnClickAffect {
            callbackClickItem?.invoke(listData[position])
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class AbsenceVH(binding: ItemAbsenceBinding) : RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding
        fun bindData(data: AbsenceModel) {
            mBinding.apply {
//                img.loadImageDrawable(data.icon)
                tvDate.text = data.createdAt.convertTimestampToString2()
                tvStatus.text =
                    if (data.status == 0) root.context.getString(R.string.absence_pending)
                    else root.context.getString(R.string.absence_approved)
            }
        }
    }

//    fun setAbsencePending(date: String) {
//        val index = listData.indexOfFirst { it.date == date }
//        listData[index] = AbsenceModel(AbsenceStatus.PENDING, date)
//        notifyItemChanged(index)
//    }
//
//    fun addNewAbsence(date: String) {
//        listData.add(0, AbsenceModel(AbsenceStatus.NOT_CONFIRMED, date))
//        notifyItemInserted(0)
//        notifyItemRangeChanged(0, itemCount)
//    }
}