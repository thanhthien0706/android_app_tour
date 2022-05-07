package com.example.appordertour.view.detail_tour

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.strictmode.FragmentStrictMode
import com.example.appordertour.R
import com.example.appordertour.model.Tour


class OverviewDetailTourFragment : Fragment() {
    private lateinit var tv_descript_detail: TextView
    private lateinit var mView: View
    private lateinit var tvDescriptDetail: TextView
    private lateinit var tvCategoryDetail: TextView
    private lateinit var tvDateDetail: TextView
    private lateinit var tvAgeDetail: TextView

    companion object {
        val ourInstance = OverviewDetailTourFragment()

        fun newInstance(): OverviewDetailTourFragment {
            return ourInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_overview_detail_tour, container, false)
        onControlers()
        onEvents()
        return mView
    }

    private fun onEvents() {
        tv_descript_detail.setText(ourInstance.arguments?.getString("descriptionTour").toString())
        tvDateDetail.setText(ourInstance.arguments?.getString("dateTour").toString())
        tvAgeDetail.setText(ourInstance.arguments?.getString("ageTour").toString())
    }

    private fun onControlers() {
        tv_descript_detail = mView.findViewById(R.id.tv_descript_detail)
        tvDescriptDetail = mView.findViewById(R.id.tv_descript_detail)
        tvCategoryDetail = mView.findViewById(R.id.tv_category_detail)
        tvDateDetail = mView.findViewById(R.id.tv_date_detail)
        tvAgeDetail = mView.findViewById(R.id.tv_age_deatail)

    }


}