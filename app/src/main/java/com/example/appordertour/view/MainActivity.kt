package com.example.appordertour.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.example.appordertour.view.adapter.ViewPagerNavMainAdapter
import com.example.appordertour.view.auth.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mNavigation: BottomNavigationView
    private lateinit var mViewPager: ViewPager
    private lateinit var mFirebase: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
//        hideSystemBars()

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
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }


    private fun addEvents() {
        mNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav_bt -> mViewPager.setCurrentItem(0)
                R.id.chat_nav_bt -> mViewPager.setCurrentItem(1)
                R.id.sale_nav_bt -> mViewPager.setCurrentItem(2)
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

        setUpViewPager()
    }

    private fun setUpViewPager() {
        val vpgAdapter = ViewPagerNavMainAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        mViewPager.adapter = vpgAdapter

    }
}
