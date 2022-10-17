package com.sonnv.kidsonline.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentFeatureBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.ui.adapter.FeatureAdapter
import com.sonnv.kidsonline.ui.adapter.feature.donve.dondon.CalendarDonAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MainViewModel

class FeatureFragment : BaseFragment<MainViewModel, FragmentFeatureBinding>() {
    private lateinit var mAdapter: FeatureAdapter


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFeatureBinding {
        return FragmentFeatureBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
    }

    override fun initView() {
        initAdapter()
    }

    override fun initObserve() {
    }

    override fun doWork() {
    }

    fun initAdapter() {
        mBinding.apply {
            context?.let {
                mAdapter = FeatureAdapter(it)
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                rcv.adapter = mAdapter

                mAdapter.setUtilityCallback { utilityModel ->
                    when (utilityModel.label) {
                        getString(R.string.label_message) -> {
                            findNavController().navigateWithAnim(R.id.chatListFragment)
                        }
                        getString(R.string.label_fee) -> {
                            findNavController().navigateWithAnim(R.id.feeFragment)
                        }

                        getString(R.string.label_leave) -> {
                            findNavController().navigateWithAnim(R.id.absenceFragment)
                        }

                        getString(R.string.label_news) -> {
                            findNavController().navigateWithAnim(R.id.newsListFragment)
                        }

                        getString(R.string.label_activity_daily) -> {
                            findNavController().navigateWithAnim(R.id.todayActivityFragment)
                        }

                        getString(R.string.label_index) -> {
                            findNavController().navigateWithAnim(R.id.childrenInfoFragment)
                        }

                        getString(R.string.label_donve) -> {
                            findNavController().navigateWithAnim(R.id.donVeFragment)
                        }

                        getString(R.string.label_danthuoc) -> {
                            findNavController().navigateWithAnim(R.id.medicineFragment)
                        }

                        else -> {
                            showMessage("Comming soon!")
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): FeatureFragment {
            return FeatureFragment()
        }
    }
}