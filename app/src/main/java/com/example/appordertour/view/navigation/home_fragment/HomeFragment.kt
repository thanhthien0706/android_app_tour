package com.example.appordertour.view.navigation.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService

class HomeFragment : Fragment {
    constructor()

    private lateinit var vpg_slide_image: ViewPager2
    private lateinit var mView: View
    private var listDataSlideImage = mutableListOf<SlideImage>()
    private var listDataTopTour = mutableListOf<Tour>()
    private lateinit var rcvCategory: RecyclerView
    private lateinit var rcvTopTour: RecyclerView

//    private var mHandler: Handler = Handler(Looper.getMainLooper())
//    private var mRunnable: Runnable = Runnable {
//        object : Runnable {
//            override fun run() {
//                var currentPosition: Int = vpg_slide_image.currentItem
//                if (currentPosition == listDataSlideImage.size - 1) {
//                    vpg_slide_image.setCurrentItem(0)
//                } else {
//                    vpg_slide_image.setCurrentItem(currentPosition + 1)
//                }
//            }
//
//        }
//    }

    private val firebase = Firebase()
    private val tourservice = TourService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView = inflater.inflate(R.layout.fragment_home, container, false)

        onControlers()
        onEvents()

        return mView
    }

    private fun onEvents() {

    }

    private fun onControlers() {
        vpg_slide_image = mView.findViewById(R.id.vpg_image_slide_home)
        rcvCategory = mView.findViewById(R.id.rcv_category_home)
        rcvTopTour = mView.findViewById(R.id.rcv_top_tour_home)

        handlerSlideImage()
        handleCategory()
        handleTopTour()
    }

    /**
     * HANDLE SLIDE IMAGE
     */
    private fun handlerSlideImage() {
        vpg_slide_image.offscreenPageLimit = 3
        vpg_slide_image.clipChildren = false
        vpg_slide_image.clipToPadding = false

        var mCompositePageTransformer = CompositePageTransformer()
        mCompositePageTransformer.addTransformer(MarginPageTransformer(30))
        mCompositePageTransformer.addTransformer(
            ViewPager2.PageTransformer { page, position ->
                val r: Float = 1 - Math.abs(position)
                page.scaleY = (0.85f + r * 0.15f)
            }
        )

        vpg_slide_image.setPageTransformer(mCompositePageTransformer)

        firebase.getSlideImage().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.getResult()) {
                    listDataSlideImage.add(
                        SlideImage(
                            document.getData().get("link_image") as String
                        )
                    )
                }

                var slideImageAdapter = SlideImageItem(listDataSlideImage)
                vpg_slide_image.adapter = slideImageAdapter

            } else {
            }
        }
    }

    /**
     * HANDLE CATEGORY
     */
    private fun handleCategory() {
        rcvCategory.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rcvCategory.adapter = categoryAdapter(setData())
    }

    /**
     * HANDLE TOPTOUR
     */

    private fun handleTopTour() {
//        rcvTopTour.setHasFixedSize(true)
        rcvTopTour.isNestedScrollingEnabled = false
        rcvTopTour.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        tourservice.getLimitTour().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.getResult()) {
                    listDataTopTour.add(
                        Tour(
                            id = document.id,
                            status = document.getData().get("status") as String?,
                            nameTour = document.getData().get("nameTour") as String?,
                            location = document.getData().get("location") as String?,
                            price = document.getData().get("price") as Long?,
                            startDate = document.getData().get("startDate") as Long?,
                            endDate = document.getData().get("endDate") as Long?,
                            adults = document.getData().get("adults") as Long?,
                            avater = document.getData().get("avater") as String?,
                            isPrivate = document.getData().get("isPrivate") as Boolean?,
                            description = document.getData().get("description") as String?,
                            listImage = document.getData().get("listImage") as List<String>?
                        )
                    )
                }

                val topTourApdapter = TopTourItem(activity, listDataTopTour)
                rcvTopTour.adapter = topTourApdapter
            }
        }


    }

    private fun setData(): MutableList<category> {
        return mutableListOf(
            category(R.drawable.luffy_5, "hay qua di", "Biển"),
            category(R.drawable.luffy_6, "hay qua di", "Rừng"),
            category(R.drawable.luffy_7, "hay qua di", "Sông"),
            category(R.drawable.luffy_8, "hay qua di", "Suối"),

            category(R.drawable.luffy_5, "hay qua di", "Biển"),
            category(R.drawable.luffy_6, "hay qua di", "Rừng"),
            category(R.drawable.luffy_7, "hay qua di", "Sông"),
            category(R.drawable.luffy_8, "hay qua di", "Suối"),
        )
    }
}