package com.sonnv.kidsonline.ui.fragment.absence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentAbsenceBinding
import com.sonnv.kidsonline.extension.addEvent
import com.sonnv.kidsonline.extension.convertTimestampToString2
import com.sonnv.kidsonline.extension.navigateWithAnim
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.AbsenceModel
import com.sonnv.kidsonline.ui.adapter.AbsenceAdapter
import com.sonnv.kidsonline.ui.dialog.AbsenceLateDialog
import com.sonnv.kidsonline.ui.dialog.ConfirmAbsenceDialog
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.MyCurrentDayDecorator
import com.sonnv.kidsonline.util.MyDayDecorator
import com.sonnv.kidsonline.util.MyDayDecorator2
import com.sonnv.kidsonline.viewmodel.AbsenceViewModel
import java.util.*

class AbsenceFragment : BaseFragment<AbsenceViewModel, FragmentAbsenceBinding>() {
    private var mAbsenceAdapter: AbsenceAdapter = AbsenceAdapter()
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAbsenceBinding = FragmentAbsenceBinding.inflate(inflater, container, false)

    override fun initViewModel() {
        mViewModel = getViewModel(AbsenceViewModel::class.java)
    }

    override fun initView() {
        initListener()
        initCalendarView()
        initAdapter()
    }

    private fun initListener() {
        mBinding.apply {
            imgAdd.setOnClickAffect {
                findNavController().navigateWithAnim(R.id.createAbsenceFragment)
            }
            tvBack.setOnClickAffect {
                activity?.onBackPressed()
            }
            mAbsenceAdapter.setCallBackClick {
                AbsenceLateDialog.newInstance(it)
                    .show(childFragmentManager, AbsenceLateDialog.TAG)
            }
        }
    }

    private fun initCalendarView() {
        mBinding.apply {
            context?.let {
                val today = MyCurrentDayDecorator(it)
                val calendar = Calendar.getInstance()
                today.setSelectedDay(
                    arrayListOf(
                        CalendarDay.from(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)
                        )
                    )
                )
                calendarView.addDecorator(today)


                calendarView.addDecorator(MyDayDecorator(it))
                calendarView.addDecorator(MyDayDecorator2(it))
            }
            calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_NONE
//            val c = CalendarDay.from(2022, 9, 10)
//            val c1 = CalendarDay.from(2022, 9, 14)
//            val c2 = CalendarDay.from(2022, 9, 17)
//            calendarView.setDateSelected(c, true)
//            calendarView.setDateSelected(c1, true)
//            calendarView.setDateSelected(c2, true)
        }
    }

    private fun initAdapter() {
        mBinding.apply {
            rcvAbsence.adapter = mAbsenceAdapter
            rcvAbsence.layoutManager = LinearLayoutManager(context)
        }

    }

    override fun initObserve() {
        mViewModel.absenceHistoryLiveData.observe(this) { data ->
            data?.let {
                showOnCalendar(it)
                mAbsenceAdapter.setListData(it)
            }
        }
    }

    private fun showOnCalendar(data: List<AbsenceModel>) {
        try {
            val absenceData: MutableList<CalendarDay> = arrayListOf()
            data.forEach {
                val date = it.createdAt.convertTimestampToString2()
                val dmy = date.split("/")
                if (dmy.size >= 3) {
                    val day = dmy[0].toInt()
                    val month = dmy[1].toInt()
                    val year = dmy[2].toInt()

                    absenceData.add(CalendarDay.from(year, month, day))
                }
            }
            context?.let {
                mBinding.calendarView.addEvent(absenceData, MyDayDecorator(it))
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getAbsenceHistory(it)
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