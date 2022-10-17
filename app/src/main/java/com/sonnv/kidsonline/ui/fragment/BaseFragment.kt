package com.sonnv.kidsonline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sonnv.kidsonline.extension.hideKeyboardClickOutside
import com.sonnv.kidsonline.ui.MainActivity
import com.sonnv.kidsonline.ui.fragment.chat.ChatFragment
import com.sonnv.kidsonline.viewmodel.BaseViewModel

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding>: Fragment() {
    protected lateinit var mBinding: VB
    protected lateinit var mViewModel: VM
    protected val mainActivity by lazy { activity as? MainActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = createViewBinding(inflater, container, savedInstanceState)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if((this is ChatFragment).not()) {
            activity?.let {
                view.hideKeyboardClickOutside(it)
            }
        }
        initView()
        initObserve()
        doWork()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    open fun loadData() {

    }


    abstract fun createViewBinding(inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   savedInstanceState: Bundle?): VB

    abstract fun initViewModel()

    abstract fun initView()

    abstract fun initObserve()

    abstract fun doWork()

    fun getViewModel(vmClass: Class<VM>): VM {
        return ViewModelProvider(this)[vmClass]
    }

    open fun showMessage(mess: String) {
        (activity as? MainActivity)?.showMessage(mess)
    }

    open fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    open fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }
}