package com.sonnv.kidsonline.ui.fragment.feature.donve.dondon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentCreateDonDonBinding
import com.sonnv.kidsonline.extension.*
import com.sonnv.kidsonline.model.HomieModel
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.getRelationShip
import com.sonnv.kidsonline.viewmodel.DonveViewModel

class CreateDonDonFragment : BaseFragment<DonveViewModel, FragmentCreateDonDonBinding>() {
    private var type: Int = 0
    private var nguoiDon: String = ""

    private var datePicked: String = ""
    private var timePicked: String = ""
    private var homieId: String = ""
    private var homieList: List<HomieModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getInt("type") ?: 0
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCreateDonDonBinding {
        return FragmentCreateDonDonBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        mViewModel = getViewModel(DonveViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            tvBack.setOnClickAffect {
                activity?.onBackPressed()
            }

            root.isClickable = true
            root.performClick()
            layoutType.imvDown.isVisible = true
            layoutType.line.isVisible = false
            som.apply {
                imvType.loadImageDrawable(R.drawable.ic_clock_don_som)
                tvType.text = getString(R.string.donSomText)
                context?.let {
                    tvType.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.color_FF7917
                        )
                    )
                }
            }
            muon.apply {
                imvType.loadImageDrawable(R.drawable.ic_clock_don_muon)
                tvType.text = getString(R.string.donMuonText)
                context?.let { tvType.setTextColor(ContextCompat.getColor(it, R.color.red_FF5959)) }
            }
            checkType()
        }
        initListener()
    }

    private fun checkType() {
        mBinding.apply {
            layoutType.apply {
                when (type) {
                    0 -> {
                        imvType.loadImageDrawable(R.drawable.ic_clock_don_dung)
                        tvType.text = getString(R.string.donDungGioText)
                        context?.let {
                            tvType.setTextColor(
                                ContextCompat.getColor(
                                    it,
                                    R.color.color_27AE60
                                )
                            )
                        }
                    }
                    1 -> {
                        imvType.loadImageDrawable(R.drawable.ic_clock_don_som)
                        tvType.text = getString(R.string.donSomText)
                        context?.let {
                            tvType.setTextColor(
                                ContextCompat.getColor(
                                    it,
                                    R.color.color_FF7917
                                )
                            )
                        }
                    }
                    2 -> {
                        imvType.loadImageDrawable(R.drawable.ic_clock_don_muon)
                        tvType.text = getString(R.string.donMuonText)
                        context?.let {
                            tvType.setTextColor(
                                ContextCompat.getColor(
                                    it,
                                    R.color.red_FF5959
                                )
                            )
                        }
                    }
                }
            }

            tvValueNguoiDon.text = nguoiDon

            containerNguoiDon.apply {
                children.forEachIndexed { index, view ->
                    val tv = (view as? TextView)
                    tv?.setDrawableLeft(
                        if (tv.text.toString().equals(nguoiDon, true)) {
                            R.drawable.ic_success
                        } else {
                            0
                        }
                    )
                }
            }
        }
    }

    private fun initListener() {
        mBinding.apply {
            viewType.setOnClickListener {
                cardSelectionType.isVisible = true
                dung.imvDown.isVisible = type == 0
                som.imvDown.isVisible = type == 1
                muon.imvDown.isVisible = type == 2
            }

            dung.root.setOnClickListener {
                type = 0
                checkType()
                cardSelectionType.isVisible = false
            }
            som.root.setOnClickListener {
                type = 1
                checkType()
                cardSelectionType.isVisible = false
            }
            muon.root.setOnClickListener {
                type = 2
                checkType()
                cardSelectionType.isVisible = false
            }
            root.doSomethingWhenClickOutside {
                checkType()
                cardSelectionType.isVisible = false
                cardSelectionNguoiDon.isVisible = false
            }

            tvValueNguoiDon.setOnClickListener {
                cardSelectionNguoiDon.isVisible = true
            }

            edtNote.setOnTouchListener { view, motionEvent ->
                checkType()
                cardSelectionType.isVisible = false
                cardSelectionNguoiDon.isVisible = false
                return@setOnTouchListener false
            }

            tvValueNgayDon.setOnClickAffect {
                context?.showDatePicker { year, month, day ->
                    val monthx = month + 1
                    tvValueNgayDon.text = "$day thg $monthx, $year"
                    datePicked = "$year-${if (monthx < 10) "0".plus(monthx) else monthx}-${
                        if (day < 10) "0".plus(day) else day
                    }"
                }
            }

            tvValueGioDon.setOnClickAffect {
                context?.showTimePicker { hour, minute ->
                    tvValueGioDon.text = "${if (hour < 10) "0".plus(hour) else hour}:${
                        if (minute < 10) "0".plus(minute) else minute
                    }"
                    timePicked = tvValueGioDon.text.toString()
                }
            }

            btnCreate.setOnClickAffect {
                context?.let {
                    mViewModel.sendPickUp(it,
                        edtNote.text.toString(),
                        type,
                        datePicked,
                        timePicked,
                        homieId
                    )
                }
            }
        }
    }

    override fun initObserve() {
        mViewModel.homemieListLiveData.observe(this) {
            it?.let {
                if (it.isNotEmpty()) {
                    homieList = it
                    homieId = it[0].id
                    nguoiDon = "${getRelationShip(it[0].relationShip)} (${it[0].name})"
                    checkType()

                    mBinding.containerNguoiDon.addHomie(context, it)

                    mBinding.containerNguoiDon.children.forEachIndexed { index, view ->
                        view.setOnClickListener {
                            nguoiDon = (it as? TextView)?.text.toString()
                            checkType()
                            mBinding.cardSelectionNguoiDon.isVisible = false

                            homieId = homieList[index].id
                        }
                    }
                }
            }
        }


        mViewModel.donvePickupLiveData.observe(this) {
            if(it.isNotEmpty()) {
                showMessage("Tạo đơn đón thành công!")
                activity?.onBackPressed()
            } else {
                showMessage("Có lỗi xảy ra!")
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getHomieList(it)
        }
    }
}