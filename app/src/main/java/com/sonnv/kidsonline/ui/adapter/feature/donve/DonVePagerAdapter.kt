package com.sonnv.kidsonline.ui.adapter.feature.donve

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonnv.kidsonline.ui.fragment.feature.donve.dondon.DonDonFragment
import com.sonnv.kidsonline.ui.fragment.feature.donve.history.HistoryDonFragment
import com.sonnv.kidsonline.ui.fragment.feature.donve.nguoidon.NguoiDonFragment

class DonVePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val listFragment = listOf(
        DonDonFragment.newInstance(),
        NguoiDonFragment.newInstance(),
        HistoryDonFragment.newInstance()
    )

    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int) = listFragment[position]
}