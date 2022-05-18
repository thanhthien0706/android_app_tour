package com.example.appordertour.view.navigation.home_fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.appordertour.R
import com.example.appordertour.model.CategoryTour
import com.example.appordertour.model.Tour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import java.util.*

class HomeFragment : Fragment {
    constructor()

    private lateinit var vpg_slide_image: ViewPager2
    private lateinit var mView: View
    private var listDataSlideImage = mutableListOf<SlideImage>()
    private var listDataTopTour = mutableListOf<Tour>()
    private var listDataCategoryTour = mutableListOf<CategoryTour>()
    private lateinit var rcvCategory: RecyclerView
    private lateinit var rcvTopTour: RecyclerView
    private lateinit var tv_name_home: TextView
    private lateinit var mTimer: Timer
    private lateinit var btn_seen_all_tour: Button

    private val mFirebase = Firebase()
    private val mTourService = TourService()

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
        btn_seen_all_tour.setOnClickListener {
            startActivity(
                Intent(activity, GetAllTourActivity::class.java)
            )
        }
    }

    private fun onControlers() {
        vpg_slide_image = mView.findViewById(R.id.vpg_image_slide_home)
        rcvCategory = mView.findViewById(R.id.rcv_category_home)
        rcvTopTour = mView.findViewById(R.id.rcv_top_tour_home)
        tv_name_home = mView.findViewById(R.id.tv_name_home)
        btn_seen_all_tour = mView.findViewById(R.id.btn_seen_all_tour)

        handlerSlideImage()
        handleCategory()
        handleTopTour()

        if (mFirebase.checkLogin()) {
            mFirebase.getUserData(mFirebase.getCurrentUser()?.uid.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (!it.result.isEmpty) {
                            val dataUser = it.result.documents[0]
                            tv_name_home.setText("Xin chào, ${dataUser.get("userName")}")
                        } else {
                            tv_name_home.setText("Chào mừng bạn đến với chúng tôi")
                        }
                    }
                }
        } else {
            tv_name_home.setText("Chào mừng bạn đến với chúng tôi")
        }

    }

    private fun autoSlideImage() {
        if (listDataSlideImage == null || listDataSlideImage.isEmpty() || vpg_slide_image == null) {
            return
        }
        mTimer = Timer()

        val timerTask = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post(Runnable {
                    fun run() {
                        var currentItem: Int = vpg_slide_image.currentItem
                        var totalItem = listDataSlideImage.size - 1
                        if (currentItem < totalItem) {
                            currentItem++
                            vpg_slide_image.setCurrentItem(currentItem)
                        } else {
                            vpg_slide_image.setCurrentItem(0)
                        }
                    }
                    run()
                })
            }

        }

        mTimer.schedule(timerTask, 200, 2000)
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



        mFirebase.getSlideImage().addOnCompleteListener { task ->
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

                autoSlideImage()

            } else {
            }
        }
    }

    /**
     * HANDLE CATEGORY
     */
    private fun handleCategory() {
        rcvCategory.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        setDataCategory()

    }

    /**
     * HANDLE TOPTOUR
     */

    private fun handleTopTour() {
//        rcvTopTour.setHasFixedSize(true)
        rcvTopTour.isNestedScrollingEnabled = false
        rcvTopTour.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        mTourService.getLimitTour().addOnCompleteListener { task ->
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
                            listImage = document.getData().get("listImage") as List<String>?,
                            categoryTour = document.getData().get("categoryTour") as String?
                        )
                    )
                }

                val topTourApdapter = TopTourItem(activity, listDataTopTour)
                rcvTopTour.adapter = topTourApdapter
            }
        }


    }

    private fun setDataCategory() {

        mTourService.getAllCategoryTour().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result) {
                    listDataCategoryTour.add(
                        CategoryTour(
                            document.id.toString().trim(),
                            document.get("resourceImage").toString(),
                            document.get("description").toString(),
                            document.get("name").toString()
                        )
                    )
                }

                rcvCategory.adapter = categoryAdapter(listDataCategoryTour)

            }
        }
//
//        return mutableListOf(
//            CategoryTour(R.drawable.luffy_5, "hay qua di", "Biển"),
//            CategoryTour(R.drawable.luffy_6, "hay qua di", "Rừng"),
//            CategoryTour(R.drawable.luffy_7, "hay qua di", "Sông"),
//            CategoryTour(R.drawable.luffy_8, "hay qua di", "Suối"),
//
//            CategoryTour(R.drawable.luffy_5, "hay qua di", "Biển"),
//            CategoryTour(R.drawable.luffy_6, "hay qua di", "Rừng"),
//            CategoryTour(R.drawable.luffy_7, "hay qua di", "Sông"),
//            CategoryTour(R.drawable.luffy_8, "hay qua di", "Suối"),
//        )
    }
}