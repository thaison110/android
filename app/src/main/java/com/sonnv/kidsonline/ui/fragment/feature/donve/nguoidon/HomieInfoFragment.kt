package com.sonnv.kidsonline.ui.fragment.feature.donve.nguoidon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentHomieInfoBinding
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.HomieModel
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.PARAM_HOMIE_DATA
import com.sonnv.kidsonline.util.getRelationShip
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class HomieInfoFragment : BaseFragment<DonveViewModel, FragmentHomieInfoBinding>() {

    private val homieData by lazy { arguments?.getSerializable(PARAM_HOMIE_DATA) as? HomieModel }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomieInfoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomieInfoBinding {
        return FragmentHomieInfoBinding.inflate(LayoutInflater.from(context))
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            tvBack.setOnClickAffect {
                activity?.onBackPressed()
            }

            homieData?.let {
                layoutAvatar.imgAvatar.loadImage(it.image)
                tvName.text = it.name
                tvFilter.text = getRelationShip(it.relationShip)
            }

            imgDelete.setOnClickListener {
                deletetHomie()
            }
        }
    }

    override fun initObserve() {
        mViewModel.deleteHomieLiveData.observe(this) {
            it?.let {
                if (it.status == 200) {
                    showMessage("Xóa thành công!")
                    activity?.onBackPressed()
                } else {
                    showMessage(it.message ?: getString(R.string.err_mess))
                }
                return@let
            }
            if (it == null) {
                showMessage(getString(R.string.err_mess))
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHomieList(it)
        }
    }

    private fun deletetHomie() {
        context?.let {
            mViewModel.deleteHomie(it, homieData?.id ?: "")
        }
    }
}