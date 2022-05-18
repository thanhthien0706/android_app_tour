package com.example.appordertour.view.navigation.home_fragment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.service.TourService

class GetAllTourActivity : AppCompatActivity() {

    private val mTourService = TourService()
    private lateinit var rcv_all_tour: RecyclerView
    private var mListTourAll = mutableListOf<Tour>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_get_all_tour)

        addControls()
    }

    private fun addControls() {
        rcv_all_tour = findViewById(R.id.rcv_all_tour)
        initData()
    }

    private fun initData() {
        rcv_all_tour.isNestedScrollingEnabled = false
        rcv_all_tour.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mTourService.getAllTour().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result.documents) {
                    mListTourAll.add(
                        Tour(
                            id = document.id,
                            status = document.get("status").toString(),
                            nameTour = document.get("nameTour").toString(),
                            location = document.get("location").toString(),
                            categoryTour = document.get("categoryTour").toString(),
                            price = document.get("price") as Long,
                            startDate = document.get("startDate") as Long,
                            endDate = document.get("endDate") as Long,
                            adults = document.get("adults") as Long,
                            avater = document.get("avater").toString(),
                            isPrivate = document.get("isPrivate") as Boolean?,
                            description = document.get("description").toString(),
                            listImage = document.get("listImage") as List<String>,
                            stoppoint = document.get("stoppoint") as List<TouristStopover>
                        )
                    )
                }
                rcv_all_tour.adapter = TopTourItem(this, mListTourAll)
            }
        }
    }
}