package com.example.appordertour.view.navigation.account

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.BuyTour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.navigation.SeenLocationActivity
import org.joda.time.DateTime
import org.joda.time.Days

class DetailMyTourActivity : AppCompatActivity() {

    private lateinit var img_detail_my_tour: ImageView
    private lateinit var tv_name_detail_my_tour: TextView
    private lateinit var tv_date_box_detail_my_tour: TextView
    private lateinit var tv_price_detail_my_tour: TextView
    private lateinit var tv_descript_detail_my_tour: TextView
    private lateinit var tv_category_detail_my_tour: TextView
    private lateinit var tv_date_detail_my_tour: TextView
    private lateinit var tv_age_deatail_my_tour: TextView
    private lateinit var tv_name_user_deatail_my_tour: TextView
    private lateinit var tv_email_deatail_my_tour: TextView
    private lateinit var tv_phone_deatail_my_tour: TextView
    private lateinit var tv_location_deatail_my_tour: TextView
    private lateinit var btn_seen_location_detail_my_tour: Button
    private lateinit var btn_cancellation_tour_detail_my_tour: Button
    private lateinit var tv_date_buy_deatail_my_tour: TextView
    private lateinit var tv_thankyou_payment: TextView

    private lateinit var objectMyTourDetail: BuyTour
    private val mBaseCustom = BaseCustom()
    private val mTourService = TourService()
    private val mFirebase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_my_tour)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addCotrols()
        addEvents()
    }

    private fun addEvents() {

        btn_cancellation_tour_detail_my_tour.setOnClickListener {
            mTourService.removeBuyTour(objectMyTourDetail.id) { status ->
                if (status) {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Không thể hủy tour", Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_seen_location_detail_my_tour.setOnClickListener {
            startActivity(
                Intent(this, SeenLocationActivity::class.java)
            )
        }
    }

    private fun addCotrols() {
        img_detail_my_tour = findViewById(R.id.img_detail_my_tour)
        tv_name_detail_my_tour = findViewById(R.id.tv_name_detail_my_tour)
        tv_date_box_detail_my_tour = findViewById(R.id.tv_date_box_detail_my_tour)
        tv_price_detail_my_tour = findViewById(R.id.tv_price_detail_my_tour)
        tv_descript_detail_my_tour = findViewById(R.id.tv_descript_detail_my_tour)
        tv_category_detail_my_tour = findViewById(R.id.tv_category_detail_my_tour)
        tv_date_detail_my_tour = findViewById(R.id.tv_date_detail_my_tour)
        tv_age_deatail_my_tour = findViewById(R.id.tv_age_deatail_my_tour)
        tv_name_user_deatail_my_tour = findViewById(R.id.tv_name_user_deatail_my_tour)
        tv_email_deatail_my_tour = findViewById(R.id.tv_email_deatail_my_tour)
        tv_phone_deatail_my_tour = findViewById(R.id.tv_phone_deatail_my_tour)
        tv_location_deatail_my_tour = findViewById(R.id.tv_location_deatail_my_tour)
        btn_seen_location_detail_my_tour = findViewById(R.id.btn_seen_location_detail_my_tour)
        btn_cancellation_tour_detail_my_tour =
            findViewById(R.id.btn_cancellation_tour_detail_my_tour)
        tv_date_buy_deatail_my_tour = findViewById(R.id.tv_date_buy_deatail_my_tour)
        tv_thankyou_payment = findViewById(R.id.tv_thankyou_payment)

        initDataMain()
    }

    private fun initDataMain() {
        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        objectMyTourDetail = bundle.get("object_my_tour") as BuyTour

        initDataTour()
        initDataUser()

        Log.d("chao_test_nha", objectMyTourDetail.statusPayment.toString())

        if (objectMyTourDetail.statusPayment) {
            btn_cancellation_tour_detail_my_tour.visibility = View.GONE
            btn_seen_location_detail_my_tour.visibility = View.GONE
        } else {
            tv_thankyou_payment.visibility = View.GONE
        }
    }

    private fun initDataUser() {
        mFirebase.getUserData(mFirebase.getCurrentUser()?.uid.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]
                    tv_name_user_deatail_my_tour.setText(
                        dataUser.get("userName").toString()
                    )

                    tv_email_deatail_my_tour.setText(
                        dataUser.get("mail").toString()
                    )

                    tv_phone_deatail_my_tour.setText(
                        dataUser.get("phone").toString()
                    )

                    tv_location_deatail_my_tour.setText(
                        dataUser.get("address").toString()
                    )
                }
            }
        }

//        Ngày mua
        tv_date_buy_deatail_my_tour.setText(mBaseCustom.convertLongToTime(objectMyTourDetail.createAt))
    }

    private fun initDataTour() {
        mTourService.getTourWithId(objectMyTourDetail.idTour).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    Glide.with(this).load(it.result.data?.get("avater")).into(img_detail_my_tour)
                    tv_name_detail_my_tour.setText(it.result.data?.get("nameTour").toString())
                    tv_price_detail_my_tour.setText(
                        mBaseCustom.convertLongtoCurrency(it.result.get("price") as Long?)
                    )
                    tv_date_box_detail_my_tour.setText(
                        "${
                            Days.daysBetween(
                                DateTime(it.result.get("endDate")),
                                DateTime(it.result.get("startDate"))
                            ).days.toString()
                        } ngày"
                    )
                    tv_descript_detail_my_tour.setText(
                        it.result.data?.get("description").toString()
                    )
                    tv_date_detail_my_tour.setText(
                        "${mBaseCustom.convertLongToTime(it.result.get("startDate") as Long?)} - ${
                            mBaseCustom.convertLongToTime(it.result.get("endDate") as Long?)
                        }"
                    )
                    tv_age_deatail_my_tour.setText("${it.result.get("adults")} tuổi")
                }
            }
        }
    }
}