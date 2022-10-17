package com.sonnv.kidsonline.ui.fragment.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentInfoChildrenTabBinding
import com.sonnv.kidsonline.extension.loadImage
import com.sonnv.kidsonline.extension.setDrawableLeft
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.UserInfo
import com.sonnv.kidsonline.ui.dialog.GalleryPickerDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.ui.fragment.main.ProfileFragment
import com.sonnv.kidsonline.util.Gender
import com.sonnv.kidsonline.viewmodel.AccountViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException


class InfoChildrenTabFragment : BaseFragment<AccountViewModel, FragmentInfoChildrenTabBinding>() {
    private var mIsEdit: Boolean = false
    private var image: String? = null
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentInfoChildrenTabBinding {
        return FragmentInfoChildrenTabBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity?.setPermissionCallback {
            showGalleryPicker()
        }
    }

    override fun initViewModel() {
        mViewModel = getViewModel(AccountViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            val gender = when (itemMale.tvFeildValue.text.toString()) {
                Gender.FEMALE.gender -> Gender.FEMALE.type
                Gender.MALE.gender -> Gender.MALE.type
                else -> Gender.OTHER.type
            }
            imgEditInfo.setOnClickListener {
                mIsEdit = !mIsEdit
                editView()
            }
            btnUpdateInfo.setOnClickAffect {
                setClearForcus()
                mIsEdit = !mIsEdit
                editView()
                context?.let { ct ->
                    showProgress()
                    mViewModel.changeStudentInfo(
                        ct,
                        tvName.text.toString(),
                        itemUser.tvFeildValue.text.toString(),
                        itemUnion.tvFeildValue.text.toString(),
                        gender, image ?: ""
                    )
                }
            }
            imgCamera.setOnClickListener {
                mIsEdit = !mIsEdit
                editView()

                if (mainActivity?.checkPermission() == false) {
                    mainActivity?.requestPermission()
                    return@setOnClickListener
                }

                showGalleryPicker()
            }
        }
    }

    private fun showGalleryPicker() {
        val dialog = GalleryPickerDialog.newInstance()
        dialog.applySingleChoice()
        dialog.setCallback { data ->
            if (data.isNotEmpty()) {
                image = data[0].getPathFromUri(context)
                mBinding.imgAvatar.loadImage(image ?: "")
            }
        }
        dialog.show(childFragmentManager, null)
    }

    private fun requestPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                context?.let {
//                    Toast.makeText(it, "Permission Granted", Toast.LENGTH_SHORT).show()
                    openImagePicker()
                }
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                context?.let {
                    Toast.makeText(it, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check()
    }

    private fun openImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val uri = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                mBinding.imgAvatar.setImageBitmap(bitmap)
                image = getUriToUpload(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getUriToUpload(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun setDataItem(userInfo: UserInfo) {
        mBinding.apply {
            tvName.setText(userInfo.fullname)
            imgAvatar.loadImage(userInfo.avatar)

            itemUnion.tvFeildLabel.text = getString(R.string.dateOfBirthLabel)
            itemUnion.tvFeildLabel.setDrawableLeft(R.drawable.ic_union)
            itemUnion.tvFeildValue.setText(userInfo.birthday)

            itemUser.tvFeildLabel.text = getString(R.string.nicknameLabel)
            itemUser.tvFeildLabel.setDrawableLeft(R.drawable.ic_user)
            itemUser.tvFeildValue.setText(userInfo.nickName.ifEmpty { "Không có" })

            itemMale.tvFeildLabel.text = getString(R.string.maleLabel)
            itemMale.tvFeildLabel.setDrawableLeft(R.drawable.ic_male)
            itemMale.tvFeildValue.setText(
                when (userInfo.gender) {
                    Gender.FEMALE.type -> Gender.FEMALE.gender
                    Gender.MALE.type -> Gender.MALE.gender
                    else -> Gender.OTHER.gender
                }
            )

            itemSchool.tvFeildLabel.text = getString(R.string.schoolLabel)
            itemSchool.tvFeildLabel.setDrawableLeft(R.drawable.ic_bank)
            itemSchool.tvFeildValue.setText("Trường Mầm non Họa Mi")

            itemGraduate.tvFeildLabel.text = getString(R.string.classLabel)
            itemGraduate.tvFeildLabel.setDrawableLeft(R.drawable.ic_graduate)
            itemGraduate.tvFeildValue.setText(userInfo.className)
        }
    }

    private fun editView() {
        mBinding.apply {
            tvEdit.isVisible = !mIsEdit
            imgEditInfo.isVisible = !mIsEdit
            btnUpdateInfo.isVisible = mIsEdit
            itemUser.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemMale.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemUnion.tvFeildValue.isFocusableInTouchMode = mIsEdit
            itemSchool.tvFeildValue.alpha = if (mIsEdit) 0.4f else 1f
            itemGraduate.tvFeildValue.alpha = if (mIsEdit) 0.4f else 1f
            tvName.isFocusableInTouchMode = mIsEdit
        }
    }

    private fun setClearForcus() {
        mBinding.apply {
            itemUser.tvFeildValue.clearFocus()
            itemMale.tvFeildValue.clearFocus()
            itemUnion.tvFeildValue.clearFocus()
            tvName.clearFocus()
        }
    }

    override fun initObserve() {
        (parentFragment as? ProfileFragment)?.homeViewModel?.userInfoLiveData?.observe(this) {
            it?.let {
                setDataItem(it)
            }
        }

        mViewModel.studentInfoLiveData.observe(this) { userModel ->
            hideProgress()
            if (userModel != null) {
                context?.let {
                    showMessage("Thông tin đã được cập nhật!")
                    setDataItem(userModel)
                }
            } else {
                showMessage("Thông tin thay đổi không thành công!")
            }
        }
    }

    override fun doWork() {
    }

    companion object {
        private const val REQUEST_CODE = 100
        fun newInstance(): InfoChildrenTabFragment {
            return InfoChildrenTabFragment()
        }
    }
}