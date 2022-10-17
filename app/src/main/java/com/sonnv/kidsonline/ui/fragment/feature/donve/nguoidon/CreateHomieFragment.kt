package com.sonnv.kidsonline.ui.fragment.feature.donve.nguoidon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentCreateNguoiDonBinding
import com.sonnv.kidsonline.extension.*
import com.sonnv.kidsonline.model.ImageModel
import com.sonnv.kidsonline.ui.dialog.GalleryPickerDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.RelationShip
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class CreateHomieFragment : BaseFragment<DonveViewModel, FragmentCreateNguoiDonBinding>() {

    private var mImageSelected: ImageModel? = null
    private var mRelationShip: Int = RelationShip.ME.key

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateHomieFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity?.setPermissionCallback {
            showGalleryPicker()
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCreateNguoiDonBinding {
        return FragmentCreateNguoiDonBinding.inflate(LayoutInflater.from(context))
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            root.doSomethingWhenClickOutside {
                cardSelectionRelationship.isVisible = false
            }

            containerRelationship.addRelationship(context, RelationShip.values())
            containerRelationship.forEachIndexed { index, view ->
                view.setOnClickListener {
                    tvFilter.text = (view as? TextView)?.text ?: RelationShip.ME.value
                    cardSelectionRelationship.isVisible = false
                    mRelationShip = RelationShip.values()[index].key
                }
            }

            tvFilter.setOnClickListener {
                cardSelectionRelationship.isVisible = true
            }

            tvBack.setOnClickAffect {
                activity?.onBackPressed()
            }

            layoutAvatar.root.setOnClickListener {
                if (mainActivity?.checkPermission() == false) {
                    mainActivity?.requestPermission()
                    return@setOnClickListener
                }
                showGalleryPicker()
            }

            btnCreate.setOnClickAffect {
                createHomie()
            }
        }
    }

    private fun showGalleryPicker() {
        val dialog = GalleryPickerDialog.newInstance()
        dialog.applySingleChoice()
        dialog.setCallback { data ->
            if (data.isNotEmpty()) {
                mImageSelected = data[0]
                val path = data[0].getPathFromUri(mBinding.root.context) ?: ""
                mBinding.layoutAvatar.imgAvatar.loadImage(path)
            }
        }
        dialog.show(childFragmentManager, null)
    }

    override fun initObserve() {
        mViewModel.createHomieLiveData.observe(this) {
            hideProgress()
            if (it.isNullOrEmpty().not()) {
                showMessage(getString(R.string.create_homie_success))
                activity?.onBackPressed()
            } else {
                showMessage(getString(R.string.err_mess))
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHomieList(it)
        }
    }

    private fun createHomie() {
        context?.let {
            val name = mBinding.edtName.text.toString()
            if (mImageSelected == null) {
                showMessage("Vui lòng chọn ảnh người đón")
                return@let
            }
            if (name.isEmpty()) {
                showMessage("Vui lòng nhập tên người đón")
                return@let
            }
            showProgress()
            mViewModel.createHomie(
                it,
                name,
                mImageSelected?.getPathFromUri(it) ?: "",
                mRelationShip
            )
        }
    }
}