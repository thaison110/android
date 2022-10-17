package com.sonnv.kidsonline.extension

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.*
import com.sonnv.kidsonline.model.HomieModel
import com.sonnv.kidsonline.model.TodayActivity
import com.sonnv.kidsonline.model.response.HealthInfo
import com.sonnv.kidsonline.util.MyDayDecorator
import com.sonnv.kidsonline.util.RelationShip
import com.sonnv.kidsonline.util.getRelationShip
import java.util.*

fun View.setOnClickAffect(onClick: (View) -> Unit?) {
    this.setOnTouchListener { v, motionEvent ->
        when (motionEvent?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.alpha = 0.5f
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                v?.alpha = 1f
            }
        }
        false
    }
    this.setOnClickListener {
        onClick.invoke(it)
    }
}

fun ImageView.loadImageDrawable(icon: Int) {
    Glide.with(this)
        .load(icon)
        .into(this)
}

fun ImageView.loadImage(icon: String) {
    Glide.with(this)
        .load(icon)
        .placeholder(R.drawable.ic_logo_app)
        .into(this)
}

fun TextView.setDrawableLeft(icon: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
}

fun LinearLayout.addActivity(context: Context?, data: List<TodayActivity>?) {
    this.removeAllViews()
    data?.forEach {
        val item = ItemTimeLineActivityTodayBinding.inflate(LayoutInflater.from(context))
        item.apply {
            tvTitle.text = it.title
            tvTime.text = it.time
            tvNote.text = it.note

            tvNote.isVisible = it.note.isNotEmpty()
        }

        this.addView(item.root)
    }

    if (data.isNullOrEmpty()) {
        val item = CommonNodataBinding.inflate(LayoutInflater.from(context))
        this.addView(item.root)
    }

    this.invalidate()
}

fun LinearLayout.addHomie(context: Context?, data: List<HomieModel>?) {
    this.removeAllViews()
    data?.forEach {
        val item = ItemHomieSimpleBinding.inflate(LayoutInflater.from(context))
        item.apply {
            tvName.text = "${getRelationShip(it.relationShip)} (${it.name})"
        }

        this.addView(item.root)
    }

    if (data.isNullOrEmpty()) {
        val item = CommonNodataBinding.inflate(LayoutInflater.from(context))
        this.addView(item.root)
    }

    this.invalidate()
}

fun LinearLayout.addYear(context: Context?, data: List<String>?) {
    this.removeAllViews()
    data?.forEach {
        val item = ItemYearSimpleBinding.inflate(LayoutInflater.from(context))
        item.apply {
            tvYear.text = "Năm $it"
        }

        this.addView(item.root)
    }

    if (data.isNullOrEmpty()) {
        val item = CommonNodataBinding.inflate(LayoutInflater.from(context))
        this.addView(item.root)
    }

    this.invalidate()
}

fun LinearLayout.addRelationship(context: Context?, data: Array<RelationShip>?) {
    this.removeAllViews()
    data?.forEach {
        val item = ItemYearSimpleBinding.inflate(LayoutInflater.from(context))
        item.apply {
            tvYear.text = it.value
        }

        this.addView(item.root)
    }

    if (data.isNullOrEmpty()) {
        val item = CommonNodataBinding.inflate(LayoutInflater.from(context))
        this.addView(item.root)
    }

    this.invalidate()
}

fun Context.showDatePicker(callback: ((Int, Int, Int) -> Unit)? = null) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(
        this,
        { view, year, month, day ->
            callback?.invoke(year, month, day)
        },
        year, month, day
    )
    datePicker.show()
}

fun Context.showTimePicker(callback: ((Int, Int) -> Unit)? = null) {
    val calendar = Calendar.getInstance()
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePicker = TimePickerDialog(
        this,
        { view, hour, minute ->
            callback?.invoke(hour, minute)
        },
        hourOfDay, minute, true
    )
    timePicker.show()
}

fun View.enableButton(isEnable: Boolean) {
    this.isEnabled = isEnabled
    if (isEnable) {
        this.alpha = 1f
    } else {
        this.alpha = 0.4f
    }
}

fun MaterialCalendarView.addEvent(data: List<CalendarDay>?, decorView: MyDayDecorator) {
    data?.let {
        decorView.setSelectedDay(it)
        this.addDecorator(decorView)
    }
}

fun HorizontalScrollView.addHealthIndex(context: Context?, data: List<HealthInfo>?) {
    val dt = data?.reversed() ?: arrayListOf()
    this.removeAllViews()
    val groupView = LinearLayout(context)
    val layoutParam = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    groupView.layoutParams = layoutParam
    groupView.gravity = Gravity.CENTER_VERTICAL or Gravity.BOTTOM
    dt.forEach { info ->
        val itemHealth = ItemHealthIndexBinding.inflate(LayoutInflater.from(context), groupView, false)
        itemHealth.apply {
            tvDate.text = info.date.toString().convertTimestampDDMM()

            val hLayoutParams = viewHeight.layoutParams
            hLayoutParams.height = info.height * 2

            val wLayoutParams = viewWeight.layoutParams
            wLayoutParams.height = info.weight * 3
        }
        groupView.addView(itemHealth.root)
    }
    this.addView(groupView)
}