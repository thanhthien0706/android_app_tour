package com.example.appordertour.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.appordertour.R
import com.example.appordertour.databinding.ActivityMainBinding
import com.example.appordertour.view.adapter.ViewPagerNavMainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vpgMain: ViewPager
    private lateinit var navViewMain: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addControls()
        addEvents()

    }

    private fun addEvents() {
        onEventHandleNavigation()

    }

    private fun onEventHandleNavigation() {
        navViewMain.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav_bt -> {
                    vpgMain.setCurrentItem(0)
                }
                R.id.chat_nav_bt -> {
                    vpgMain.setCurrentItem(1)
                }
                R.id.sale_nav_bt -> {
                    vpgMain.setCurrentItem(2)
                }
                R.id.order_nav_bt -> {
                    vpgMain.setCurrentItem(3)
                }
                R.id.account_nav_bt -> {
                    vpgMain.setCurrentItem(4)
                }
            }
            true
        }
    }

    private fun addControls() {
        vpgMain = findViewById(R.id.vpg_main)
        navViewMain = binding.navViewMain

        setUpViewPagerMain()
    }

    private fun setUpViewPagerMain() {
        val vpgNavMainAdapter = ViewPagerNavMainAdapter(
            supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        vpgMain.adapter = vpgNavMainAdapter

        vpgMain?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> navViewMain.menu.findItem(R.id.home_nav_bt).setChecked(true)
                    1 -> navViewMain.menu.findItem(R.id.chat_nav_bt).setChecked(true)
                    2 -> navViewMain.menu.findItem(R.id.sale_nav_bt).setChecked(true)
                    3 -> navViewMain.menu.findItem(R.id.order_nav_bt).setChecked(true)
                    4 -> navViewMain.menu.findItem(R.id.account_nav_bt).setChecked(true)
                }
            }

        })
    }
}