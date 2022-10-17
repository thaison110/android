package com.sonnv.kidsonline.ui.adapter.feature.donve.dondon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sonnv.kidsonline.databinding.ItemDonveCalendarBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplay
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.convertTimestampToString3
import com.sonnv.kidsonline.model.DonveModel
import com.sonnv.kidsonline.util.DonveType

class CalendarDonAdapter: RecyclerView.Adapter<CalendarDonAdapter.ViewHolder>() {

    var mData: MutableList<DonveModel> = arrayListOf()

    fun setData(data: List<DonveModel>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mBinding: ItemDonveCalendarBinding): RecyclerView.ViewHolder(mBinding.root) {
        fun bindView(pos: Int) {
            val dataItem = mData[pos]
            mBinding.apply {
                root.setOnClickListener {

                }
                tvTime.text = dataItem.createdAt.convertTimestampToString2()
                tvNguoiDon.text = dataItem.homieName
                tvType.text =
                    when(dataItem.type) {
                        DonveType.DON_MUON.ordinal -> DonveType.DON_MUON.value
                        DonveType.DON_SOM.ordinal -> DonveType.DON_MUON.value
                        else  -> DonveType.DUNG_GIO.value
                    }
                tvCreateTime.text = "Đã tạo lúc " + dataItem.createdAt.convertTimestampToString3()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDonveCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}