package com.sonnv.kidsonline.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.isVisible
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentNewsDetailBinding
import com.sonnv.kidsonline.extension.convertTimeToDisplay
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.loadImageDrawable
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.NewsModel
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.NewsViewModel

class NewsDetailFragment : BaseFragment<NewsViewModel, FragmentNewsDetailBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsDetailBinding {
        return FragmentNewsDetailBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(NewsViewModel::class.java)
    }

    override fun initView() {
        mBinding.tvLabel.setOnClickAffect {
            activity?.onBackPressed()
        }

        mBinding.webView.webChromeClient = object : WebChromeClient() {

        }
        mBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        initWebSettings(mBinding.webView.settings)
    }

    override fun initObserve() {
        mViewModel.newDetailLiveData.observe(this) {
            it?.let {
                setViewDetail(it)
            }
        }
    }

    private fun setViewDetail(newsModel: NewsModel) {
        mBinding.apply {
            webView.loadData(newsModel.detail, "text/html; charset=utf-8", "UTF-8")
            imgBall.loadImageDrawable(if (newsModel.type == 0) R.drawable.ic_user else R.drawable.ic_graduate)
            tvTitle.text = newsModel.name
            tvImportant.isVisible = newsModel.isImportant == 1
            tvTime.text = newsModel.time.toString().convertTimeToDisplay()
            imgThumb.loadImage(newsModel.image)
        }
    }

    override fun doWork() {
        val newID = arguments?.getInt("new_id")
        newID?.let {
            mViewModel.getNewDetail(newID)
        }
    }

    private fun initWebSettings(settings: WebSettings) {
        settings.apply {
            builtInZoomControls = true
            setSupportZoom(true)
            javaScriptEnabled = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            allowContentAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            useWideViewPort = false
            mixedContentMode = 0
        }
    }
}