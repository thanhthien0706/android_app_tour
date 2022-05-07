package com.example.appordertour.view.detail_tour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.view.detail_tour.Timeline.TimelineAdapter

class ActivitiesDetailTourFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var rcvTimelineDetail: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_activities_detail_tour, container, false)
        onControlers()
        onEvents()
        return mView
    }

    private fun onEvents() {

    }

    private fun onControlers() {
        rcvTimelineDetail = mView.findViewById(R.id.rcv_timeline_detail)
        rcvTimelineDetail.layoutManager =
            LinearLayoutManager(activity)

        rcvTimelineDetail.adapter = TimelineAdapter(setData())
        ViewCompat.setNestedScrollingEnabled(rcvTimelineDetail, false)

    }

    private fun setData(): List<TouristStopover> {
        return listOf(
            TouristStopover(
                name = "thien 1",
                descripton = "rat la hay luon ne",
                location = "Nha trang",
                time = "12h"
            ),
            TouristStopover(
                name = "thien 2",
                descripton = "rat la hay luon ne",
                location = "Nha trang",
                time = "12h"
            ),
            TouristStopover(
                name = "thien 3",
                descripton = "rat la hay luon ne",
                location = "Nha trang",
                time = "12h"
            ), TouristStopover(
                name = "thien 4",
                descripton = "rat la hay luon ne",
                location = "Nha trang",
                time = "12h"
            )
        )
    }

}