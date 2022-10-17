package com.sonnv.kidsonline.ui.fragment.feature.donve.nguoidon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentHomieListBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.ui.adapter.feature.donve.HomieAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_HOMIE_DATA
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class NguoiDonFragment : BaseFragment<DonveViewModel, FragmentHomieListBinding>() {

    private val mAdapter: HomieAdapter = HomieAdapter()

    companion object {
        @JvmStatic
        fun newInstance() =
            NguoiDonFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomieListBinding {
        return FragmentHomieListBinding.inflate(LayoutInflater.from(context))
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            imgAdd.setOnClickListener {
                findNavController().navigateWithAnim(R.id.createHomieFragment)
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            rcv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rcv.adapter = mAdapter

            mAdapter.setClickCallback {
                findNavController().navigateWithAnim(
                    R.id.infoHomieFragment,
                bundleOf(PARAM_HOMIE_DATA to  it))
            }
        }
    }

    override fun initObserve() {
        mViewModel.homemieListLiveData.observe(this) {
            it?.let {
                mAdapter.setData(it)
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHomieList(it)
        }
    }
}