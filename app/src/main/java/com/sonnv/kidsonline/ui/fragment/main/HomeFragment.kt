package com.sonnv.kidsonline.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentHomeBinding
import com.sonnv.kidsonline.extension.addActivity
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.model.response.LoginData
import com.sonnv.kidsonline.ui.adapter.HomeAdapter
import com.sonnv.kidsonline.ui.adapter.news.NewsListAdapter
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_LOGIN_DATA
import com.sonnv.kidsonline.viewmodel.AbsenceViewModel
import com.sonnv.kidsonline.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private lateinit var mAdapter: HomeAdapter
    private lateinit var mNewsAdapter: NewsListAdapter
    private lateinit var absenceViewModel: AbsenceViewModel

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(HomeViewModel::class.java)
        absenceViewModel = ViewModelProvider(this)[AbsenceViewModel::class.java]
    }

    override fun initView() {
        mBinding.infoView.setOnClickAffect {
            (parentFragment as? MainFragment)?.switchTab(MainFragment.PageNumber.PROFILE_PAGE.ordinal)
        }
        mBinding.icActivity.root.setOnClickAffect {
            findNavController().navigateWithAnim(R.id.todayActivityFragment)
        }
//        mBinding.icSchool.root.setOnClickAffect {
//            findNavController().navigateWithAnim(
//                R.id.newsDetailFragment,
//                bundleOf("news_type" to FeedType.NEWS_SCHOOL)
//            )
//        }
//        mBinding.icTeacher.root.setOnClickAffect {
//            findNavController().navigateWithAnim(
//                R.id.newsDetailFragment,
//                bundleOf("news_type" to FeedType.NEWS_TEACHER)
//            )
//        }
        mBinding.tvSeeAll.setOnClickAffect {
            findNavController().navigateWithAnim(R.id.newsListFragment)
        }

        initAdapter()
    }

    private fun initAdapter() {
        mBinding.apply {
            context?.let { ct ->
                mNewsAdapter = NewsListAdapter(ct)
                mNewsAdapter.setNewsCallback { news ->
                    findNavController().navigateWithAnim(
                        R.id.newsDetailFragment,
                        bundleOf(
                            "news_type" to news.getFeedType(),
                            "new_id" to news.id
                        )
                    )
                }
                rcvNews.adapter = mNewsAdapter
                rcvNews.layoutManager = LinearLayoutManager(ct, RecyclerView.VERTICAL, false)

                rcvInfo.layoutManager = LinearLayoutManager(ct, RecyclerView.VERTICAL, false)
                mAdapter = HomeAdapter(ct)
                rcvInfo.adapter = mAdapter
                mAdapter.setEditClickCallback {
                    (parentFragment as? MainFragment)?.switchTab(MainFragment.PageNumber.FEATURE_PAGE.ordinal)
                }
                mAdapter.setUtilityCallback {
                    when (it.label) {
                        getString(R.string.label_message) -> {
                            findNavController().navigateWithAnim(R.id.chatListFragment)
                        }

                        getString(R.string.label_leave) -> {
                            findNavController().navigateWithAnim(R.id.absenceFragment)
                        }

                        getString(R.string.label_index) -> {
                            findNavController().navigateWithAnim(R.id.childrenInfoFragment)
                        }

                        getString(R.string.label_donve) -> {
                            findNavController().navigateWithAnim(R.id.donVeFragment)
                        }

                        else -> {
                            showMessage("Comming soon!")
                        }
                    }
                }
            }
        }
    }

    override fun initObserve() {
        mViewModel.userInfoLiveData.observe(this) {
            it?.let {
                bindUserInfoView(it)
            }
        }

        absenceViewModel.activateLiveData.observe(this) {
            bindActivates(it)
        }
    }

    private fun bindActivates(it: List<TodayActivity>?) {
        mBinding.apply {
            icActivity.llActivity.addActivity(context, it)
            icActivity.root.isVisible = it?.isNotEmpty() ?: false
        }
    }

    private fun bindNewsList(it: List<NewsModel>?) {
        mNewsAdapter.setData(it)
    }

    private fun bindUserInfoView(userInfo: UserInfo) {
        mBinding.apply {
            tvKidName.text = userInfo.fullname
            tvClass.text = userInfo.className
            imgAvatar.loadImage(userInfo.avatar)
        }
    }

    override fun doWork() {
        mViewModel.getUserInfo()
        context?.let {
            absenceViewModel.getActivate(it)
        }
        mainActivity?.apply {
            val loginData = intent.getSerializableExtra(PARAM_LOGIN_DATA) as? LoginData
            loginData?.let {
                bindActivates(it.activates)
                bindNewsList(it.newsList)
            }
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}