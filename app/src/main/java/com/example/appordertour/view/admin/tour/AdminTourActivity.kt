package com.example.appordertour.view.admin.tour

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appordertour.databinding.ActivityAdminTourBinding
import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.service.TourService

class AdminTourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminTourBinding

    private var mTourService = TourService()
    private var mListTourAll: MutableList<Tour> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityAdminTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onControls()
        onEvents()
    }

    private fun onEvents() {
        binding.btnBackAdmin.setOnClickListener {
            finish()
        }

        binding.menuAdmin.setOnClickListener {
            startActivity(
                Intent(this, AddTourActivity::class.java)
            )
        }
    }

    private fun onControls() {
        initDataTour()

        binding.rcvTourAdmin.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun initDataTour() {
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
                binding.rcvTourAdmin.adapter = TourAdminAdapter(mListTourAll)


            }
        }
    }

}