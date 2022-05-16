package com.example.appordertour.view.navigation.booking

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
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.ThankYouActivity
import com.example.appordertour.view.navigation.account.InformationAccountActivity
import org.joda.time.DateTime
import org.joda.time.Days

class DetailBookingTourActivity : AppCompatActivity() {

    private lateinit var imgDetailBookingTour: ImageView
    private lateinit var tv_name_detail_booking_tour: TextView
    private lateinit var tv_date_box_detail_booking_tour: TextView
    private lateinit var tv_price_detail_booking_tour: TextView
    private lateinit var tv_descript_detail_booking_tour: TextView
    private lateinit var tv_category_detail_booking_tour: TextView
    private lateinit var tv_date_detail_booking_tour: TextView
    private lateinit var tv_age_deatail_booking_tour: TextView
    private lateinit var tv_name_user_deatail_booking_tour: TextView
    private lateinit var tv_email_deatail_booking_tour: TextView
    private lateinit var tv_phone_deatail_booking_tour: TextView
    private lateinit var tv_location_deatail_booking_tour: TextView
    private lateinit var btn_buy_now_detail_booking_tour: Button
    private lateinit var btn_delete_detail_booking_tour: Button
    private lateinit var tv_change_data_detail_booking_tour: TextView

    private lateinit var objectBookingTourDetail: ItemIdTour
    private val mBaseCustom = BaseCustom()
    private val mTourService = TourService()
    private val mFirebase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_booking_tour)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addControls()
        addEvents()
    }

    private fun addEvents() {

        btn_buy_now_detail_booking_tour.setOnClickListener { handleBuyTour() }
        btn_delete_detail_booking_tour.setOnClickListener {
            mTourService.removeBookingTourWithId(
                objectBookingTourDetail.idTour,
                objectBookingTourDetail.createAt,
                mFirebase.getCurrentUser()?.uid.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(
                        Intent(
                            this, MainActivity::class.java
                        )
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_LONG).show()
                }
            }
        }
        tv_change_data_detail_booking_tour.setOnClickListener {
            startActivity(
                Intent(this, InformationAccountActivity::class.java)
            )
        }
    }

    private fun handleBuyTour() {
        if (tv_name_user_deatail_booking_tour.text == "" || tv_email_deatail_booking_tour.text == "" || tv_phone_deatail_booking_tour.text == "" || tv_location_deatail_booking_tour.text == "") {
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
        } else {

            mTourService.getOrderTourWithId(mFirebase.getCurrentUser()?.uid.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val listTourBooking =
                            it.result.data?.get("listIdTour") as MutableList<*>

                        val newArrayTourBooking = mutableListOf<ItemIdTour>()

                        for (document in listTourBooking) {
                            val dataDocument = document as HashMap<String, ItemIdTour>
                            if (dataDocument.get("idTour")
                                    .toString() == objectBookingTourDetail.idTour.toString()
                            ) {
                                newArrayTourBooking.add(
                                    ItemIdTour(
                                        dataDocument.get("idTour").toString(),
                                        "sold",
                                        dataDocument.get("createAt") as Long
                                    )
                                )
                            } else {
                                newArrayTourBooking.add(
                                    ItemIdTour(
                                        dataDocument.get("idTour").toString(),
                                        dataDocument.get("statusBooking").toString(),
                                        dataDocument.get("createAt") as Long
                                    )
                                )
                            }
                        }

                        mTourService.updateTourBookingWithId(
                            mFirebase.getCurrentUser()?.uid.toString(),
                            newArrayTourBooking
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {

                                startActivity(
                                    Intent(this, ThankYouActivity::class.java)
                                )
                                finish()

                            }
                        }

                    }
                }

        }
    }

    private fun addControls() {
        imgDetailBookingTour = findViewById(R.id.img_detail_booking_tour)
        tv_name_detail_booking_tour = findViewById(R.id.tv_name_detail_booking_tour)
        tv_date_box_detail_booking_tour = findViewById(R.id.tv_date_box_detail_booking_tour)
        tv_price_detail_booking_tour = findViewById(R.id.tv_price_detail_booking_tour)
        tv_descript_detail_booking_tour = findViewById(R.id.tv_descript_detail_booking_tour)
        tv_category_detail_booking_tour = findViewById(R.id.tv_category_detail_booking_tour)
        tv_date_detail_booking_tour = findViewById(R.id.tv_date_detail_booking_tour)
        tv_age_deatail_booking_tour = findViewById(R.id.tv_age_deatail_booking_tour)
        tv_name_user_deatail_booking_tour = findViewById(R.id.tv_name_user_deatail_booking_tour)
        tv_email_deatail_booking_tour = findViewById(R.id.tv_email_deatail_booking_tour)
        tv_phone_deatail_booking_tour = findViewById(R.id.tv_phone_deatail_booking_tour)
        tv_location_deatail_booking_tour = findViewById(R.id.tv_location_deatail_booking_tour)
        btn_buy_now_detail_booking_tour = findViewById(R.id.btn_buy_now_detail_booking_tour)
        btn_delete_detail_booking_tour = findViewById(R.id.btn_delete_detail_booking_tour)
        tv_change_data_detail_booking_tour = findViewById(R.id.tv_change_data_detail_booking_tour)

        initDataMain()
    }

    private fun initDataMain() {
        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        objectBookingTourDetail = bundle.get("object_booking_tour") as ItemIdTour

        initDataTour()
        initDataUser()
    }

    private fun initDataUser() {
        mFirebase.getUserData(mFirebase.getCurrentUser()?.uid.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]
                    tv_name_user_deatail_booking_tour.setText(
                        dataUser.get("userName").toString()
                    )

                    tv_email_deatail_booking_tour.setText(
                        dataUser.get("mail").toString()
                    )

                    tv_phone_deatail_booking_tour.setText(
                        dataUser.get("phone").toString()
                    )

                    tv_location_deatail_booking_tour.setText(
                        dataUser.get("address").toString()
                    )
                }
            }
        }
    }

    private fun initDataTour() {
        mTourService.getTourWithId(objectBookingTourDetail.idTour).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    Glide.with(this).load(it.result.data?.get("avater")).into(imgDetailBookingTour)
                    tv_name_detail_booking_tour.setText(it.result.data?.get("nameTour").toString())
                    tv_price_detail_booking_tour.setText(
                        mBaseCustom.convertLongtoCurrency(it.result.get("price") as Long?)
                    )
                    tv_date_box_detail_booking_tour.setText(
                        "${
                            Days.daysBetween(
                                DateTime(it.result.get("endDate")),
                                DateTime(it.result.get("startDate"))
                            ).days.toString()
                        } ngày"
                    )
                    tv_descript_detail_booking_tour.setText(
                        it.result.data?.get("description").toString()
                    )
                    tv_date_detail_booking_tour.setText(
                        "${mBaseCustom.convertLongToTime(it.result.get("startDate") as Long?)} - ${
                            mBaseCustom.convertLongToTime(it.result.get("endDate") as Long?)
                        }"
                    )
                    tv_age_deatail_booking_tour.setText("${it.result.get("adults")} tuổi")
                }
            }
        }
    }
}