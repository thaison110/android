package com.sonnv.kidsonline.ui.adapter.medicine

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.sonnv.kidsonline.extension.loadImage


class MedicineImagePagerAdapter(val context: Context): PagerAdapter() {
    private var imageList: List<String> = arrayListOf()

    fun setData(imageList: List<String>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER
        imageView.loadImage(imageList[position])
        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}