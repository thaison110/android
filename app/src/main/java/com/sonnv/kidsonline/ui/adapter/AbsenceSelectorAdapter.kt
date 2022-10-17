package com.sonnv.kidsonline.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sonnv.kidsonline.databinding.ItemAbsenceSelectedBinding

class AbsenceSelectorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<CalendarDay> = arrayListOf()

    private var callback: ((List<CalendarDay>) -> Unit?) ?= null

    fun setCallback(callback: ((List<CalendarDay>) -> Unit?) ?= null) {
        this.callback = callback
    }

    fun setDataList(dt: List<CalendarDay>) {
        data.clear()
        data.addAll(dt)
        notifyDataSetChanged()
    }

    fun deleteItem(day: CalendarDay) {
        data.forEach {
            if (it.day == day.day
                && it.month == day.month
                && it.year == day.year
            ) {
                data.remove(it)
                notifyDataSetChanged()
                return
            }
        }
    }

    fun addItem(day: CalendarDay) {
        data.add(day)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AbsenceVH(ItemAbsenceSelectedBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AbsenceVH).bindData(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class AbsenceVH(binding: ItemAbsenceSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding

        fun bindData(position: Int, itemData: CalendarDay) {
            mBinding.apply {
                tvDay.text = "${itemData.day} Th${itemData.month}, ${itemData.year}"

                imgClose.setOnClickListener {
                    data.remove(itemData)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)

                    callback?.invoke(data)
                }
            }
        }
    }
}