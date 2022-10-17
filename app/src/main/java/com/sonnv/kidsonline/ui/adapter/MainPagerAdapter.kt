package com.sonnv.kidsonline.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonnv.kidsonline.ui.fragment.main.FeatureFragment
import com.sonnv.kidsonline.ui.fragment.main.HomeFragment
import com.sonnv.kidsonline.ui.fragment.main.ProfileFragment
import com.sonnv.kidsonline.ui.fragment.main.NotificationListFragment

class MainPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private var listFragments = arrayListOf(
        HomeFragment.newInstance(),
        FeatureFragment.newInstance(),
        NotificationListFragment.newInstance(),
        ProfileFragment.newInstance()
    )

    override fun getItemCount(): Int = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}