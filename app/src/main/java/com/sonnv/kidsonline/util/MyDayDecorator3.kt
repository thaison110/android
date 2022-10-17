package com.sonnv.kidsonline.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.sonnv.kidsonline.R

class MyDayDecorator3(val context: Context) : DayViewDecorator {
    private var selectedDay: List<CalendarDay> = arrayListOf()
    private var drawable3: Drawable = context.resources.getDrawable(R.drawable.bg_absence_di_muon)

    fun setSelectedDay(selectedDay: List<CalendarDay>) {
        this.selectedDay = selectedDay
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return isContains(day)
    }

    override fun decorate(view: DayViewFacade?) {
//        view?.addSpan(BackgroundColorSpan(android.graphics.Color.GREEN))
        view?.setSelectionDrawable(drawable3)
    }

    private fun isContains(day: CalendarDay?): Boolean {
        day?.let {
            selectedDay.forEach { day ->
                if (day.day == it.day
                    && day.month == it.month
                    && day.year == it.year
                ) {
                    return true
                }
            }
        }
        return false
    }
}