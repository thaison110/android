package com.sonnv.kidsonline.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.sonnv.kidsonline.R

class MyCurrentDayDecorator(val context: Context) : DayViewDecorator {
    private var selectedDay: List<CalendarDay> = arrayListOf()

    private var drawable: Drawable = context.resources.getDrawable(R.drawable.bg_absence_todat)

    fun setSelectedDay(selectedDay: List<CalendarDay>) {
        this.selectedDay = selectedDay
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return isContains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(drawable)
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