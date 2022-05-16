package com.example.appordertour.view.navigation.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.model.Tour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.view.navigation.home_fragment.TopTourItem

class OrderFragment : Fragment {
    constructor()

    private lateinit var mView: View
    private var listDataBookingTour = mutableListOf<ItemIdTour>()
    private lateinit var rcvBookingTour: RecyclerView
    private lateinit var adapterBookingTour: ItemBookingTour

    private val mTourService = TourService()
    private val mFireBase = Firebase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_order, container, false)

        addControlls()
        addEvents()
        return mView
    }

    private fun addEvents() {

    }

    private fun addControlls() {

        rcvBookingTour = mView.findViewById(R.id.rcv_booking_tour)

        initData()
    }

    private fun initData() {
        rcvBookingTour.isNestedScrollingEnabled = false
        rcvBookingTour.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        mTourService.getOrderTourWithId(mFireBase.getCurrentUser()!!.uid).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
//                    it.result.get("listIdTour")
                    for (document in it.result.get("listIdTour") as ArrayList<*>) {
                        var dataTour = document as HashMap<String, ItemIdTour>

                        listDataBookingTour.add(
                            ItemIdTour(
                                dataTour.get("idTour").toString(),
                                dataTour.get("statusBooking").toString(),
                                dataTour.get("createAt") as Long
                            )
                        )
                    }

                    val bookingTourApdapter = ItemBookingTour(listDataBookingTour)
                    rcvBookingTour.adapter = bookingTourApdapter

                }
            }
        }
    }
}