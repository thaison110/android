package com.sonnv.kidsonline.ui.fragment.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sonnv.kidsonline.databinding.FragmentActivityTodayBinding
import com.sonnv.kidsonline.extension.addActivity
import com.sonnv.kidsonline.extension.getDayOfWeek
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.fragment.BaseFragment
import com.sonnv.kidsonline.util.getYMD
import com.sonnv.kidsonline.viewmodel.AbsenceViewModel
import java.util.*

class TodayActivityFragment : BaseFragment<AbsenceViewModel, FragmentActivityTodayBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentActivityTodayBinding =
        FragmentActivityTodayBinding.inflate(inflater, container, false)

    override fun initViewModel() {
        mViewModel = getViewModel(AbsenceViewModel::class.java)
    }

    override fun initView() {
        mBinding.apply {
            tvTitle.setOnClickAffect {
                activity?.onBackPressed()
            }
        }
        initAdapter()
        initCalendarView()
    }

    private fun initCalendarView() {
        mBinding.apply {
            val day = getYMD(Calendar.DAY_OF_MONTH)
            val month = getYMD(Calendar.MONTH)
            val year = getYMD(Calendar.YEAR)
            displayTime(day, month, year)


            calendarView.setOnDateChangeListener { calendarView, year, month, day ->
                Log.d("XXXXXX", "$year $month $day")
                displayTime(day, month, year)

                val monthStr = if (month + 1 < 10) "0${month + 1}" else (month + 1)
                context?.let {
                    mViewModel.getActivate(it, "$year-$monthStr-$day")
                }
            }
        }
    }

    private fun displayTime(day: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK).getDayOfWeek()


        val monthStr = if (month + 1 < 10) "0${month + 1}" else (month + 1)
        mBinding.tvDay.text = "$dayOfWeek, Ngày $day/$monthStr/$year"
    }

    private fun initAdapter() {

    }

    override fun initObserve() {
        mViewModel.activateLiveData.observe(this) {
            mBinding.apply {
                llActivity.addActivity(context, it)
            }
        }
    }

    override fun doWork() {
        context?.let {
            mViewModel.getActivate(it)
        }
    }

    companion object {
        fun newInstance(): TodayActivityFragment {
            val args = Bundle()

            val fragment = TodayActivityFragment()
            fragment.arguments = args
            return fragment
        }
    }
}