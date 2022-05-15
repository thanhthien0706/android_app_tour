package com.example.appordertour.view.detail_tour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.strictmode.FragmentStrictMode
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.view.auth.LoginActivity


class OverviewDetailTourFragment : Fragment() {
    private lateinit var tv_descript_detail: TextView
    private lateinit var mView: View
    private lateinit var tvDescriptDetail: TextView
    private lateinit var tvCategoryDetail: TextView
    private lateinit var tvDateDetail: TextView
    private lateinit var tvAgeDetail: TextView
    private lateinit var btnBuyNow: Button
    private lateinit var btnAddOrder: ImageButton

    private val mFirebase = Firebase()
    private val mTourService = TourService()

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

        btnBuyNow.setOnClickListener {
            if (mFirebase.checkLogin()) {

            } else {
                startActivity(
                    Intent(activity, LoginActivity::class.java)
                )
            }
        }

        btnAddOrder.setOnClickListener {
            handleAddOrderTour()
        }
    }

    private fun handleAddOrderTour() {

        mTourService.checkOrderTourExist(
            ourInstance.arguments?.getString("idTour").toString(),
            mFirebase.getCurrentUser()?.uid.toString()
        ) { status ->
            if (status) {
//                mTourService.addOrderTour(
//                    ourInstance.arguments?.getString("idTour").toString(),
//                    mFirebase.getCurrentUser()?.uid.toString()
//                ).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        Toast.makeText(activity, "Đã thêm tour!", Toast.LENGTH_LONG).show();
//                    }
//                }
                Toast.makeText(activity, "Bạn đã đặt tour này", Toast.LENGTH_LONG).show();
            } else {
                mTourService.createOrderTour(
                    ourInstance.arguments?.getString("idTour").toString(),
                    mFirebase.getCurrentUser()?.uid.toString()
                ) { status ->
                    if (status) {
                        Toast.makeText(activity, "Đã thêm tour!", Toast.LENGTH_LONG).show();
                    }
                }

                Toast.makeText(activity, "Khong tồn tại", Toast.LENGTH_LONG).show();
            }
        }

    }

    private fun onControlers() {
        tv_descript_detail = mView.findViewById(R.id.tv_descript_detail)
        tvDescriptDetail = mView.findViewById(R.id.tv_descript_detail)
        tvCategoryDetail = mView.findViewById(R.id.tv_category_detail)
        tvDateDetail = mView.findViewById(R.id.tv_date_detail)
        tvAgeDetail = mView.findViewById(R.id.tv_age_deatail)
        btnBuyNow = mView.findViewById(R.id.btn_buy_now)
        btnAddOrder = mView.findViewById(R.id.btn_add_order)

    }


}