package com.sonnv.kidsonline.ui.adapter.medicine

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.databinding.ItemMedicineLayoutBinding
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.MedicineTicketModel

class MedicineHistoryAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData: List<MedicineTicketModel> = arrayListOf()

    private var medicineCallback: ((MedicineTicketModel) -> Unit?)? = null

    fun setData(mList: List<MedicineTicketModel>?) {
        mList?.let {
            mData = mList
            notifyDataSetChanged()
        }
    }

    fun setMedicineCallback(callback: ((MedicineTicketModel) -> Unit)? = null) {
        medicineCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MedicineVH(
            ItemMedicineLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MedicineVH)?.bindData(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MedicineVH(binding: ItemMedicineLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: ItemMedicineLayoutBinding = binding

        fun bindData(position: Int) {
            val medicine = mData[position]
            binding.apply {
                root.setOnClickAffect {
                    medicineCallback?.invoke(medicine)
                }

                imgAvatar.loadImage(if (medicine.images.isNotEmpty()) medicine.images[0] else "")
                tvDate.text = medicine.createdAt.convertTimestampToString2()
                tvNote.text = medicine.note

                if (medicine.status == 0) {
                    tvConfirm.text = Html.fromHtml(
                        String.format("<font color=\"#FF5959\">Chưa xác nhận</font>"),
                        Html.FROM_HTML_MODE_LEGACY
                    )
                } else {
                    tvConfirm.text = Html.fromHtml(
                        String.format("<font color=\"#005CC8\">Đã xác nhận</font> bởi ${medicine.confirmBy}"),
                        Html.FROM_HTML_MODE_LEGACY
                    )
                }
            }
        }
    }
}