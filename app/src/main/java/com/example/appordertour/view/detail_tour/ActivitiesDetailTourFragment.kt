package com.example.appordertour.view.detail_tour

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.service.TourService
import com.example.appordertour.view.detail_tour.Timeline.TimelineAdapter

class ActivitiesDetailTourFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var rcvTimelineDetail: RecyclerView;
    private var mListDataStopoint = mutableListOf<TouristStopover>()

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


        ViewCompat.setNestedScrollingEnabled(rcvTimelineDetail, false)

        setData()

    }

    private fun setData() {

        mTourService.getTourWithId(ourInstance.arguments?.getString("idTour").toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result.data?.get("stoppoint") as ArrayList<*>) {
                        var documentStoppoint = document as HashMap<String, TouristStopover>
                        mListDataStopoint.add(
                            TouristStopover(
                                name = documentStoppoint.get("name").toString(),
                                descripton = documentStoppoint.get("descript").toString(),
                                location = documentStoppoint.get("location").toString(),
                                time = documentStoppoint.get("time").toString()
                            )
                        )
                    }
                    rcvTimelineDetail.adapter = TimelineAdapter(mListDataStopoint)
                }
            }

    }

}