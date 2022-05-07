package com.example.appordertour.view.adapter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.auth.RegisterActivity
import com.example.appordertour.view.navigation.*
import com.example.appordertour.view.navigation.home_fragment.HomeFragment

class ViewPagerNavMainAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ChatFragment()
            2 -> SaleFragment()
            3 -> OrderFragment()
            4 -> AccountFragment()
            else -> HomeFragment()
        }
    }

}