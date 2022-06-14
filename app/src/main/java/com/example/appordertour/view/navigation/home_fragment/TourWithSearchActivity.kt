package com.example.appordertour.view.navigation.home_fragment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appordertour.databinding.ActivityAdminEditTourBinding
import com.example.appordertour.databinding.ActivityTourWithSearchBinding
import com.example.appordertour.model.Tour
import com.example.appordertour.service.TourService

class TourWithSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTourWithSearchBinding

    private lateinit var textSearchTour: String
    private var listDataTour = mutableListOf<Tour>()
    private var listSearch: MutableList<String> = mutableListOf()

    private val mTourService = TourService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityTourWithSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        textSearchTour = bundle.get("textSearch") as String

        onControls()
        onEvents()
    }

    private fun onEvents() {
        binding.btnBackDetailTourCategory.setOnClickListener {
            finish()
        }
    }

    private fun onControls() {

        listSearch.add(textSearchTour)
        binding.tvNameCategory.setText(textSearchTour)
        initDataMain()

        binding.rcvSearchTour.isNestedScrollingEnabled = false
        binding.rcvSearchTour.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initDataMain() {
        mTourService.getTourWithSearch(listSearch) {
            listDataTour.addAll(it)

            binding.rcvSearchTour.adapter = TopTourItem(this, listDataTour)
        }
    }
}