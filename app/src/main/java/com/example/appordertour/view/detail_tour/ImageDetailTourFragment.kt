package com.example.appordertour.view.detail_tour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import java.util.ArrayList

class ImageDetailTourFragment : Fragment() {
    private val listImages: Array<String>? = null
    private lateinit var mView: View
    private lateinit var rcvImageDetailTour: RecyclerView

    companion object {
        val ourInstance = ImageDetailTourFragment()

        fun newInstance(): ImageDetailTourFragment {
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
        mView = inflater.inflate(R.layout.fragment_image_detail_tour, container, false)
        onControlers()
        onEvents()
        return mView
    }

    private fun onEvents() {

    }

    private fun onControlers() {
        rcvImageDetailTour = mView.findViewById(R.id.rcv_image_detail_tour)

        val dataTest: Tour = ourInstance.arguments?.get("tour_student") as Tour

        rcvImageDetailTour.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rcvImageDetailTour.adapter =
            ItemImageDetailTour(ourInstance.arguments?.get("listImageTour") as ArrayList<String>)
    }
}