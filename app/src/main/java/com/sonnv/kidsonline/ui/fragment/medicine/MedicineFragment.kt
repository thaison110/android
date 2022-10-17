package com.sonnv.kidsonline.ui.fragment.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentMedicineListBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.MedicineTicketModel
import com.sonnv.kidsonline.ui.adapter.medicine.MedicineHistoryAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MedicineViewModel

class MedicineFragment : BaseFragment<MedicineViewModel, FragmentMedicineListBinding>() {

    private lateinit var mAdapter: MedicineHistoryAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            MedicineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMedicineListBinding {
        return FragmentMedicineListBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(MedicineViewModel::class.java)
    }

    override fun initView() {
        initListener()
        initAdapter()
    }

    private fun initAdapter() {
        context?.let {
            mAdapter = MedicineHistoryAdapter(it)
            mBinding.apply {
                rcv.layoutManager = LinearLayoutManager(context)
                rcv.adapter = mAdapter
            }

            mAdapter.setMedicineCallback {
                goToDetailMedicineFragment(it)
            }
        }
    }

    private fun goToDetailMedicineFragment(it: MedicineTicketModel) {
        findNavController().navigateWithAnim(
            R.id.medicineDetailFragment,
            bundleOf(MedicineDetailFragment.MEDICINE_DATA to it)
        )
    }

    private fun initListener() {
        mBinding.apply {
            btnNewMedicine.setOnClickAffect {
                goToCreateMedicineFragment()
            }
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
    }

    private fun goToCreateMedicineFragment() {
        findNavController().navigateWithAnim(
            R.id.medicineCreateFragment
        )
    }


    override fun initObserve() {
        mViewModel.medicineHistoryLiveData.observe(this) {
            it?.let {
                mAdapter.setData(it)
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHistoryMedicine(it)
        }
    }
}