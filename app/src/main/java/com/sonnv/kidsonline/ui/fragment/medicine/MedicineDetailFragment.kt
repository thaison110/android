package com.sonnv.kidsonline.ui.fragment.medicine

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sonnv.kidsonline.databinding.FragmentMedicineDetailBinding
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.MedicineTicketModel
import com.sonnv.kidsonline.ui.adapter.medicine.MedicineImagePagerAdapter
import com.sonnv.kidsonline.ui.dialog.ConfirmDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MedicineViewModel
import java.text.SimpleDateFormat
import java.util.*

class MedicineDetailFragment : BaseFragment<MedicineViewModel, FragmentMedicineDetailBinding>() {

    private lateinit var mPagerAdapter: MedicineImagePagerAdapter
    private val medicineDetail by lazy { arguments?.getSerializable(MEDICINE_DATA) as? MedicineTicketModel }

    companion object {
        val MEDICINE_DATA = "MEDICINE_DATA"
        @JvmStatic
        fun newInstance() =
            MedicineDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMedicineDetailBinding {
        return FragmentMedicineDetailBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(MedicineViewModel::class.java)
    }

    override fun initView() {
        initListener()
        initPagerAdapter()
        bindData()
    }

    private fun initPagerAdapter() {
        context?.let {
            mPagerAdapter = MedicineImagePagerAdapter(it)
            mBinding.apply {
                vpg.adapter = mPagerAdapter
                tabLayout.setupWithViewPager(vpg)
            }
        }
    }

    private fun bindData() {
        medicineDetail?.let {
            mBinding.apply {
                tvDate.text = it.createdAt.convertTimestampToString2()
                tvNotiDate.text = convertTimestampToString(it.createdAt.toLong())

                if (it.status == 0) {
                    tvConfirm.text = Html.fromHtml(
                        String.format("<font color=\"#FF5959\">Chưa xác nhận</font>"),
                        Html.FROM_HTML_MODE_LEGACY
                    )
                } else {
                    tvConfirm.text = Html.fromHtml(
                        String.format("<font color=\"#005CC8\">Đã xác nhận</font> bởi ${it.confirmBy}"),
                        Html.FROM_HTML_MODE_LEGACY
                    )
                }
                tvNote.text = it.note

                mPagerAdapter.setData(it.images)
            }
        }
    }


    private fun initListener() {
        mBinding.apply {
            imgDelete.setOnClickAffect {
                showConfirmDialog()
            }
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
    }

    private fun showConfirmDialog() {
        val confirmDialog = ConfirmDialog.newInstance()
        confirmDialog.setCallback {
            deleteMedicine()
        }
        confirmDialog.show(childFragmentManager, null)
    }

    private fun deleteMedicine() {
        context?.let { ct ->
            medicineDetail?.let {
                mViewModel.deleteMedicine(ct, it.id, "")
            }
        }
    }


    override fun initObserve() {
        mViewModel.deleteMedicineLiveData.observe(this) {
            if (it.status == 200) {
                showMessage("Xóa đơn thuốc thành công!")
                activity?.onBackPressed()
            } else {
                showMessage(it.message ?: "Có lỗi xảy ra")
            }
        }
    }

    override fun doWork() {
    }

    private fun convertTimestampToString(tim: Long): String {
        val time = tim * 1000
        val sdf = SimpleDateFormat("hh:mm")
        val netDate = Date(time)

        val sdf2 = SimpleDateFormat("dd")
        val netDate2 = Date(time)

        val sdf3 = SimpleDateFormat("MM, yyyy")
        val netDate3 = Date(time)

        return "Đã tạo vào lúc ".plus(sdf.format(netDate)
            .plus(", ngày ").plus(sdf2.format(netDate2)))
            .plus(" Tháng ").plus(sdf3.format(netDate3))
    }
}