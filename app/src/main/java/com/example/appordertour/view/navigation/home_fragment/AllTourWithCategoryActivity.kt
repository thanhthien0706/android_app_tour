package com.example.appordertour.view.navigation.home_fragment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.CategoryTour
import com.example.appordertour.model.Tour
import com.example.appordertour.service.TourService

class AllTourWithCategoryActivity : AppCompatActivity() {

    private lateinit var btn_back_detail_tour_category: ImageButton
    private lateinit var tv_name_category: TextView
    private lateinit var rcv_tour_with_category: RecyclerView
    private var listDataTopTour = mutableListOf<Tour>()

    private lateinit var categoryTourData: CategoryTour

    private val mTourService = TourService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tour_with_category)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addControls()
        addEvents()
    }

    private fun addEvents() {

        btn_back_detail_tour_category.setOnClickListener {
            finish()
        }
    }

    private fun addControls() {
        btn_back_detail_tour_category = findViewById(R.id.btn_back_detail_tour_category)
        tv_name_category = findViewById(R.id.tv_name_category)
        rcv_tour_with_category = findViewById(R.id.rcv_tour_with_category)

        rcv_tour_with_category.isNestedScrollingEnabled = false
        rcv_tour_with_category.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        categoryTourData = bundle.get("object_category") as CategoryTour

        if (categoryTourData != null) {
            tv_name_category.setText(categoryTourData.name)


            mTourService.getTourWithCategoryTour(
                categoryTourData.id.toString()
            ) { listTour ->

                listDataTopTour.addAll(listTour)

                val topTourApdapter = TopTourItem(this, listDataTopTour)
                rcv_tour_with_category.adapter = topTourApdapter
            }
        }

    }
}