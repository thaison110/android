package com.sonnv.kidsonline.ui.fragment.absence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentCreateAbsenceBinding
import com.sonnv.kidsonline.extension.enableButton
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.adapter.AbsenceSelectorAdapter
import com.sonnv.kidsonline.ui.dialog.ConfirmAbsenceDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.MyCurrentDayDecorator
import com.sonnv.kidsonline.util.MyDayDecorator2
import com.sonnv.kidsonline.viewmodel.AbsenceViewModel
import java.util.*

class CreateAbsenceFragment : BaseFragment<AbsenceViewModel, FragmentCreateAbsenceBinding>() {
    private var mAbsenceAdapter: AbsenceSelectorAdapter = AbsenceSelectorAdapter()
    private val calendarSelected by lazy { context?.let { MyDayDecorator2(it) } }

    private lateinit var mCalendar: Calendar
    var currentDay: Int = 0
    var currentMonth: Int = 0
    var currentYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCalendar = Calendar.getInstance()
        currentDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = mCalendar.get(Calendar.MONTH)
        currentYear = mCalendar.get(Calendar.YEAR)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCreateAbsenceBinding =
        FragmentCreateAbsenceBinding.inflate(inflater, container, false)

    override fun initViewModel() {
        mViewModel = getViewModel(AbsenceViewModel::class.java)
    }

    override fun initView() {
        initListener()
        initAdapter()
        initCalendarView()
    }

    private fun initAdapter() {
        mBinding.apply {
            rcvAbsence.layoutManager = GridLayoutManager(context, 2)
            rcvAbsence.adapter = mAbsenceAdapter

            mAbsenceAdapter.setCallback {
                context?.let { ct ->
                    showSelectedDate()
                    checkHasSelectedData()
                }
            }
        }
    }

    private fun initListener() {
        mBinding.apply {
            tvTitle.setOnClickAffect {
                activity?.onBackPressed()
            }

            btnSend.setOnClickAffect {
                sendAbsence()
            }
        }
    }

    private fun sendAbsence() {
        mBinding.apply {
            context?.let {
                showProgress()
                val data = mAbsenceAdapter.data
                mViewModel.sendAbsence(it, edtReason.text.toString(), data)
            }
        }
    }

    private fun initCalendarView() {
        mBinding.apply {
            calendarView.addDecorator(calendarSelected)
            calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE

            calendarView.setOnDateChangedListener { widget, date, selected ->
                if(!isSelectValid(date)) {
                    showSelectedDate()
                    return@setOnDateChangedListener
                }

                if (selected) {
                    mAbsenceAdapter.addItem(date)
                } else {
                    mAbsenceAdapter.deleteItem(date)
                }
                tvAbsenceDayNumber.text = getString(
                    R.string.label_absence_day_number,
                    mAbsenceAdapter.itemCount.toString()
                )

                checkHasSelectedData()
            }
        }
    }

    private fun showSelectedDate() {
        mBinding.apply {
            calendarView.removeDecorator(calendarSelected)
            calendarView.clearSelection()
            calendarSelected?.setSelectedDay(mAbsenceAdapter.data)
            calendarView.addDecorator(calendarSelected)
        }
    }

    private fun isSelectValid(date: CalendarDay): Boolean {
        val calendarSunday = Calendar.getInstance()
        calendarSunday.set(date.year, date.month, date.day)
        if (calendarSunday.get(Calendar.DAY_OF_WEEK) - 3 == Calendar.SUNDAY) {
            showMessage("Bạn không thể xin nghỉ vào Chủ Nhật")
            return false
        }

        val calendar = Calendar.getInstance()
        calendar.set(currentYear, currentMonth, currentDay)
        val first = CalendarDay.from(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)
        )

        val last = CalendarDay.from(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 2, calendar.get(Calendar.DAY_OF_MONTH)
        )

        if (date.isBefore(first)) {
            showMessage("Bạn không thể chọn ngày đã qua!")
            return false
        }

        if (date.isInRange(first, last).not()) {
            showMessage("Bạn không thể chọn quá 30 ngày!")
            return false
        }

        return true
    }

    override fun initObserve() {
        mViewModel.absenceSendLiveData.observe(this) {
            hideProgress()

            if (it.status == 200) {
                showMessage(getString(R.string.create_absence_success))
                activity?.onBackPressed()
            } else {
                showMessage(it.message ?: "Có lỗi xảy ra!")
            }
        }
    }

    override fun doWork() {
        context?.let {
//            mViewModel.getAbsenceHistory(it)
        }
    }

    fun checkHasSelectedData() {
        mBinding.apply {
            layoutDaySelected.isVisible = mAbsenceAdapter.itemCount > 0
            btnSend.enableButton(mAbsenceAdapter.itemCount > 0)
        }
    }

    private fun showDialogConfirm(date: String) {
        activity?.supportFragmentManager?.let { at ->
            ConfirmAbsenceDialog(date) {
//                mAbsenceAdapter.setAbsencePending(it)
//                mDayAdapter.changeAbsencePending(it)
            }.show(
                at,
                this::class.java.simpleName
            )
        }
    }
}