package com.sonnv.kidsonline.ui.fragment.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.sonnv.kidsonline.databinding.FragmentMedicineCreateBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.extension.showDatePicker
import com.sonnv.kidsonline.model.ImageModel
import com.sonnv.kidsonline.ui.MainActivity
import com.sonnv.kidsonline.ui.adapter.medicine.MedicineImagePagerAdapter
import com.sonnv.kidsonline.ui.dialog.GalleryPickerDialog
import com.sonnv.kidsonline.ui.dialog.MedicineConfirmDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.viewmodel.MedicineViewModel
import java.util.*

class CreateMedicineFragment : BaseFragment<MedicineViewModel, FragmentMedicineCreateBinding>() {

    private lateinit var mPagerAdapter: MedicineImagePagerAdapter
    private var startDatePicked: String = ""
    private var endDatePicked: String = ""
    private var mImageData: List<ImageModel> = arrayListOf()

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateMedicineFragment().apply {
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
    ): FragmentMedicineCreateBinding {
        return FragmentMedicineCreateBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(MedicineViewModel::class.java)
    }

    override fun initView() {
        initListener()
        initPagerAdapter()
        bindData()
    }

    private fun initPagerAdapter() {
        context?.let {
            mPagerAdapter = MedicineImagePagerAdapter(it)
            mBinding.apply {
                vpg.adapter = mPagerAdapter
                tabLayout.setupWithViewPager(vpg)
            }
        }
    }

    private fun bindData() {
        mBinding.apply {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            val currentDay = "$day Thg ${month + 1}, $year"
            val currentPicked = "$year-${if (month < 10) "0".plus(month) else month}-${
                if (day < 10) "0".plus(day) else day
            }"

            tvStartDate.text = currentDay
            tvEndDate.text = currentDay

            startDatePicked = currentPicked
            endDatePicked = currentPicked
        }
    }


    private fun initListener() {
        mBinding.apply {
            btnCreate.setOnClickAffect {
                showConfirmDialog()
            }

            tvStartDate.setOnClickAffect {
                context?.showDatePicker { year, month, day ->
                    val monthx = month + 1
                    tvStartDate.text = "$day Thg $monthx, $year"
                    startDatePicked = "$year-${if (monthx < 10) "0".plus(monthx) else monthx}-${
                        if (day < 10) "0".plus(day) else day
                    }"
                }
            }

            tvEndDate.setOnClickAffect {
                context?.showDatePicker { year, month, day ->
                    val monthx = month + 1
                    tvEndDate.text = "$day Thg $monthx, $year"
                    endDatePicked = "$year-${if (monthx < 10) "0".plus(monthx) else monthx}-${
                        if (day < 10) "0".plus(day) else day
                    }"
                }
            }

            tvAddMedicine.setOnClickListener { view ->
                if (mainActivity?.checkPermission() == false) {
                    mainActivity?.requestPermission()
                    return@setOnClickListener
                }

                showGalleryPicker()
            }
            tvLabel.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
    }

    private fun showGalleryPicker() {
        val dialog = GalleryPickerDialog.newInstance()
        dialog.setCallback { data ->
            mImageData = data

            if (data.isNotEmpty()) {
                mPagerAdapter.setData(data.map { it.getPathFromUri(context) ?: "" })
                mBinding.layoutVpg.isVisible = true
            }
        }
        dialog.show(childFragmentManager, null)
    }

    private fun showConfirmDialog() {
        if (mImageData.isNullOrEmpty()) {
            showMessage("Vui lòng thêm hình ảnh đơn thuốc!")
            return
        }
        val dialog = MedicineConfirmDialog.newInstance()
        dialog.setCallback {
            createMedicine()
            dialog.dismiss()
        }
        dialog.show(childFragmentManager, null)
    }

    private fun createMedicine() {
        context?.let {
            showProgress()

            mViewModel.sendMedicine(
                it,
                startDatePicked,
                endDatePicked,
                mImageData,
                arrayListOf(),
                mBinding.edtNote.text.toString()
            )
        }
    }


    override fun initObserve() {
        mViewModel.deleteMedicineLiveData.observe(this) {
            if (it?.status == 200) {
                showMessage("Xóa đơn thuốc thành công!")
                activity?.onBackPressed()
            } else {
                showMessage(it?.message ?: "Có lỗi xảy ra")
            }
        }

        mViewModel.sendMedicineLiveData.observe(this) {
            hideProgress()

            if (it?.status == 200) {
                showMessage("Tạo đơn thuốc thành công!")
                activity?.onBackPressed()
            } else {
                showMessage(it?.message ?: "Có lỗi xảy ra")
            }
        }
    }

    override fun doWork() {
    }
}