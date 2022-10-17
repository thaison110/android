package com.sonnv.kidsonline.ui.adapter.fee

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.ItemTuitionFeeLayoutBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.extension.setTextColorStatus
import com.sonnv.kidsonline.model.Fee

class FeeAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mCallback: ((Fee) -> Unit)? = null
    private val mData = arrayListOf(
        Fee(
            "Học phí kỳ I - 2022", "Chưa thanh toán", 28500000, 0, 28500000, "08/12/2022",
            4500000, 780000, 5, 1500000, 600000
        ),
        Fee(
            "Học phí kỳ II - 2022", "Đã thanh toán", 28500000, 28500000, 0, "08/09/2022",
            4500000, 780000, 5, 1500000, 600000
        )
    )

    fun setCallback(callback: ((Fee) -> Unit)? = null) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FeeViewHolder(
            ItemTuitionFeeLayoutBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )
        )
    }

    inner class FeeViewHolder(binding: ItemTuitionFeeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemTuitionFeeLayoutBinding = binding
        fun bindData(position: Int) {
            val fee = mData[position]
            binding.apply {
                tvLablelFee.text = fee.feeName
                tvStatus.text = fee.feeStatus
                tvStatus.setTextColorStatus(fee.feeStatus == "Đã thanh toán")
                tvTotalValue.text =
                    if (fee.total == 0) fee.total.toString() else fee.total.toString().plus("đ")
                tvDebtValue.text =
                    if (fee.debt == 0) fee.debt.toString() else fee.debt.toString().plus("đ")
                tvPaidValue.text =
                    if (fee.paid == 0) fee.paid.toString() else fee.paid.toString().plus("đ")
                tvDeadlineValue.text = fee.deadline
                root.setBackgroundResource(if (position == 0) R.drawable.bg_boder_fee_radius_12 else R.drawable.bg_gray_fee_radius_12)
                root.setOnClickAffect {
                    mCallback?.invoke(fee)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FeeViewHolder)?.bindData(position)
    }

    override fun getItemCount(): Int = mData.size
}