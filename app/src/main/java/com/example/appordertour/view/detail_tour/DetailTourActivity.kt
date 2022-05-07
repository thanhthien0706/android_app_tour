package com.example.appordertour.view.detail_tour

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.util.BaseCustom
import com.google.android.material.tabs.TabLayout
import org.joda.time.DateTime
import org.joda.time.Days

class DetailTourActivity : AppCompatActivity() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewpageMain: ViewPager
    private lateinit var mTourData: Tour
    private lateinit var img_detail_tour: ImageView
    private lateinit var tv_name_detail_tour: TextView
    private lateinit var tv_location_detail_tour: TextView
    private lateinit var tv_price_detail_tour: TextView
    private lateinit var btn_back: ImageButton
    private lateinit var tv_date_detail: TextView
    private lateinit var mViewpagerAdapter: ViewpagerDetailAdapter

    private val baseCustom = BaseCustom()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tour)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        onControls()
        onListener()
    }

    private fun onListener() {
        btn_back.setOnClickListener {
            this.finish()
        }
    }

    private fun onControls() {
        mTabLayout = findViewById(R.id.tlo_detail_tour)
        mViewpageMain = findViewById(R.id.vpg_detail_tour)
        img_detail_tour = findViewById(R.id.img_detail_tour)
        tv_name_detail_tour = findViewById(R.id.tv_name_detail_tour)
        tv_location_detail_tour = findViewById(R.id.tv_location_detail_tour)
        tv_price_detail_tour = findViewById(R.id.tv_price_detail_tour)
        btn_back = findViewById(R.id.btn_back)
        tv_date_detail = findViewById(R.id.tv_date_detail)
        mViewpagerAdapter = ViewpagerDetailAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        // FRAGMENT


        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }
        mTourData = bundle.get("tour_student") as Tour

        initViewpager()
        initData()
    }

    private fun initData() {
        Glide.with(this).load(mTourData.avater).into(img_detail_tour)
        tv_name_detail_tour.setText(mTourData.nameTour)
        tv_location_detail_tour.setText(mTourData.location)
        tv_price_detail_tour.setText(baseCustom.convertLongtoCurrency(mTourData.price))
        tv_date_detail.setText(
//            "${mTourData.endDate?.minus(mTourData.startDate!!)} ngày"
            "${
                Days.daysBetween(
                    DateTime(mTourData.endDate),
                    DateTime(mTourData.startDate)
                ).days.toString()
            } ngày"
        )
    }

    private fun initViewpager() {


        mViewpageMain.adapter = mViewpagerAdapter
        mTabLayout.setupWithViewPager(mViewpageMain)

    }
}