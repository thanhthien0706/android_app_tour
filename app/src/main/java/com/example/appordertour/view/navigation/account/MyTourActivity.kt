package com.example.appordertour.view.navigation.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.BuyTour
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.view.navigation.booking.ItemBookingTour

class MyTourActivity : AppCompatActivity() {
    private lateinit var btn_back_my_tour: ImageButton
    private lateinit var rcv_my_tour: RecyclerView
    private lateinit var tv_not_login_my_tour: TextView
    private var listDataMyTour = mutableListOf<ItemIdTour>()
    private var mListDataMyTour = mutableListOf<BuyTour>()

    private val mTourService = TourService()
    private val mFireBase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tour)

        addControls()
        addEvents()
    }

    private fun addEvents() {

        btn_back_my_tour.setOnClickListener {
            finish()
        }
    }

    private fun addControls() {
        btn_back_my_tour = findViewById(R.id.btn_back_my_tour)
        rcv_my_tour = findViewById(R.id.rcv_my_tour)
        tv_not_login_my_tour = findViewById(R.id.tv_not_login_my_tour)

        initData()


    }

    private fun initData() {
        if (mFireBase.checkLogin()) {
            tv_not_login_my_tour.visibility = View.GONE
            rcv_my_tour.isNestedScrollingEnabled = false
            rcv_my_tour.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            mTourService.getBuyTourWithId(mFireBase.getCurrentUser()!!.uid) { listBuyTour ->
                mListDataMyTour.addAll(listBuyTour)
                val myTourApdapter = ItemMyTour(mListDataMyTour)
                rcv_my_tour.adapter = myTourApdapter
            }

        } else {

        }
    }
}