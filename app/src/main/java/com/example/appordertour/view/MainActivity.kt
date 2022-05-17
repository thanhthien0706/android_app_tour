package com.example.appordertour.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.appordertour.R
import com.example.appordertour.databinding.ActivityMainBinding
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.view.adapter.ViewPagerNavMainAdapter
import com.example.appordertour.view.auth.LoginActivity
import com.example.appordertour.view.navigation.chat.ChatActivity
import com.example.appordertour.view.navigation.home_fragment.OnItemClickTour
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnItemClickTour {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mNavigation: BottomNavigationView
    private lateinit var mViewPager: ViewPager

    private lateinit var mFirebase: Firebase
    private var tourService = TourService()

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

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }


    private fun addEvents() {
        mNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav_bt -> mViewPager.setCurrentItem(0)
                R.id.chat_nav_bt -> {
                    if (mFirebase.checkLogin()) {
                        startActivity(
                            Intent(
                                applicationContext,
                                ChatActivity::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(applicationContext, LoginActivity::class.java)
                        )
                    }

                }
//                R.id.sale_nav_bt -> mViewPager.setCurrentItem(2)
                R.id.order_nav_bt -> mViewPager.setCurrentItem(3)
                R.id.account_nav_bt -> {
                    if (mFirebase.checkLogin()) {
                        mViewPager.setCurrentItem(4)
                    } else {
                        startActivity(
                            Intent(applicationContext, LoginActivity::class.java)
                        )
                    }
                }

            }
            true
        }
    }

    private fun addControls() {
        mNavigation = findViewById(R.id.nav_view_main)
        mViewPager = findViewById(R.id.vpg_main)
        mFirebase = Firebase()

        Log.d("id_user", mFirebase.getCurrentUser()?.uid.toString())

        setUpViewPager()
    }

    private fun setUpViewPager() {

        val vpgAdapter = ViewPagerNavMainAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        mViewPager.adapter = vpgAdapter
        mViewPager.setOnTouchListener(null)

    }

    override fun onItemClick(position: Int) {
        Log.d("da_vao_roi", position.toString())
    }
}
