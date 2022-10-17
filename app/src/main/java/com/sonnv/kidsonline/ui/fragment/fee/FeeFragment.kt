package com.sonnv.kidsonline.ui.fragment.fee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentFeeBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.fee.FeeAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MainViewModel

class FeeFragment : BaseFragment<MainViewModel, FragmentFeeBinding>() {
    private lateinit var mFeeAdapter: FeeAdapter
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFeeBinding {
        return FragmentFeeBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
    }

    override fun initView() {
        mBinding.apply {
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                mFeeAdapter = FeeAdapter(it)
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                rcv.adapter = mFeeAdapter
                mFeeAdapter.setCallback { feeModel ->
                    findNavController().navigateWithAnim(
                        R.id.feeDetailFragment, bundleOf(ARG_FEE_DETAIL to feeModel)
                    )
                }
            }
        }
    }

    override fun initObserve() {
    }

    override fun doWork() {
    }

    companion object {
        const val ARG_FEE_DETAIL = "ARG_FEE_DETAIL"

        fun newInstance(): FeeFragment {
            return FeeFragment()
        }
    }
}