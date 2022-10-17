package com.sonnv.kidsonline.ui.fragment.fee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentFeeDetailBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.Fee
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MainViewModel

class FeeDetailFragment : BaseFragment<MainViewModel, FragmentFeeDetailBinding>() {
    private val feeModel by lazy { arguments?.getSerializable(FeeFragment.ARG_FEE_DETAIL) as Fee }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFeeDetailBinding {
        return FragmentFeeDetailBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
    }

    override fun initView() {
        mBinding.apply {
            tvFeeNameDetail.text = getString(R.string.feeNameDetailLabel, feeModel.feeName)
            tvFeeTuitionValue.text =
                if (feeModel.tuitionFee == 0) feeModel.tuitionFee.toString() else feeModel.tuitionFee.toString()
                    .plus("đ")
            tvFeeEatValue.text =
                if (feeModel.eatFee == 0) feeModel.eatFee.toString() else feeModel.eatFee.toString()
                    .plus("đ")
            tvMonthNumberValue.text = feeModel.month.toString()
            tvLearnToolsValue.text =
                if (feeModel.learnToolsFee == 0) feeModel.learnToolsFee.toString() else feeModel.learnToolsFee.toString()
                    .plus("đ")
            tvFeePicnicValue.text =
                if (feeModel.picnicFee == 0) feeModel.picnicFee.toString() else feeModel.picnicFee.toString()
                    .plus("đ")
            tvTotalValue.text =
                if (feeModel.total == 0) feeModel.total.toString() else feeModel.total.toString()
                    .plus("đ")
            tvDeadlineValue.text = feeModel.deadline
            val isPaid = feeModel.feeStatus == "Đã thanh toán"
            tvUnpaidLabel.text = feeModel.feeStatus
            tvReminder.text =
                if (isPaid) getString(R.string.reminderPaidLabel) else getString(R.string.reminderLabel)
            tvUnpaidLabel.setBackgroundResource(if (isPaid) R.drawable.bg_green_radius_40 else R.drawable.bg_red_radius_40)

            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
    }

    override fun initObserve() {
    }

    override fun doWork() {
    }

    companion object {
        fun newInstance(): FeeDetailFragment {
            return FeeDetailFragment()
        }
    }
}