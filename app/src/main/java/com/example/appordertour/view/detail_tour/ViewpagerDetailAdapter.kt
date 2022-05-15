package com.example.appordertour.view.detail_tour

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewpagerDetailAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OverviewDetailTourFragment()
            1 -> ActivitiesDetailTourFragment()
            2 -> ImageDetailTourFragment()
            3 -> ReviewDetailTourFragment()
            else -> OverviewDetailTourFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String = ""

        when (position) {
            0 -> title = "Tổng quan"
            1 -> title = "Hoạt động"
            2 -> title = "Ảnh"
            3 -> title = "Đánh giá"
        }

        return title
    }
}