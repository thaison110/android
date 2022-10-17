package com.sonnv.kidsonline.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonnv.kidsonline.ui.fragment.profile.InfoChildrenTabFragment
import com.sonnv.kidsonline.ui.fragment.profile.InfoParentTabFragment

class ProfilePagerAdapter(activity: Fragment) : FragmentStateAdapter(activity) {
    private var listFragments = arrayListOf(
        InfoChildrenTabFragment.newInstance(),
        InfoParentTabFragment.newInstance()
    )

    override fun getItemCount(): Int = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}