package com.sonnv.kidsonline.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentNewsListBinding
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.news.NewsListAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.NewsViewModel

class NewsListFragment : BaseFragment<NewsViewModel, FragmentNewsListBinding>() {
    private lateinit var mAdapter: NewsListAdapter

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsListBinding {
        return FragmentNewsListBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(NewsViewModel::class.java)
    }

    override fun initView() {
        mBinding.btnWritePost.setOnClickAffect { }
        mBinding.tvLabel.setOnClickAffect {
            activity?.onBackPressed()
        }
        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let {
                rcv.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
                mAdapter = NewsListAdapter(it)
                rcv.adapter = mAdapter
                mAdapter.setNewsCallback { news ->
                    findNavController().navigateWithAnim(
                        R.id.newsDetailFragment,
                        bundleOf(
                            "news_type" to news.getFeedType(),
                            "new_id" to news.id
                        )
                    )
                }
            }
        }
    }

    override fun initObserve() {
        mViewModel.listNewsLiveData.observe(this) {
            it?.let {
                mAdapter.setData(it)
            }
        }
    }

    override fun doWork() {
        mViewModel.getListNews()
    }
}