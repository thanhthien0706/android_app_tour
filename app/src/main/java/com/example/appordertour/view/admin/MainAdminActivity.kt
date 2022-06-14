package com.example.appordertour.view.admin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.appordertour.R
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.MainStartActivity
import com.example.appordertour.view.admin.chat_admin.ChatAdminActivity
import com.example.appordertour.view.admin.tour.AdminTourActivity
import com.example.appordertour.view.navigation.account.InformationAccountActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainAdminActivity : AppCompatActivity() {

    private lateinit var tv_name_admin: TextView
    private lateinit var btn_signout_admin: MaterialButton
    private lateinit var btn_chat_admin: MaterialCardView
    private lateinit var btn_tour_admin: MaterialCardView
    private lateinit var btn_infor_admin: MaterialCardView
//    private lateinit var btn_review_admin: MaterialCardView
//    private lateinit var btn_statistical_admin: MaterialCardView

    private val mFirebase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addControlls()
        addEvents()
    }

    private fun addEvents() {
        btn_signout_admin.setOnClickListener {
            mFirebase.signOut()
            startActivity(
                Intent(this, MainStartActivity::class.java)
            )
            finish()
        }

        btn_chat_admin.setOnClickListener {
            startActivity(
                Intent(this, ChatAdminActivity::class.java)
            )
        }

        btn_tour_admin.setOnClickListener {
            startActivity(
                Intent(this, AdminTourActivity::class.java)
            )
        }

        btn_infor_admin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    InformationAccountActivity::class.java
                )
            )
        }
    }

    private fun addControlls() {
        tv_name_admin = findViewById(R.id.tv_name_admin)
        btn_signout_admin = findViewById(R.id.btn_signout_admin)
        btn_chat_admin = findViewById(R.id.btn_chat_admin)
        btn_tour_admin = findViewById(R.id.btn_tour_admin)
        btn_infor_admin = findViewById(R.id.btn_infor_admin)
//        btn_review_admin = findViewById(R.id.btn_review_admin)
//        btn_statistical_admin = findViewById(R.id.btn_statistical_admin)

        initData()
    }

    private fun initData() {
        if (mFirebase.checkLogin()) {
            mFirebase.getUserData(mFirebase.getCurrentUser()?.uid.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (!it.result.isEmpty) {
                            val dataUser = it.result.documents[0]
                            tv_name_admin.setText("Xin chào, ${dataUser.get("userName")}")
                        } else {
                            tv_name_admin.setText("Chào mừng bạn đến với chúng tôi")
                        }
                    }
                }
        } else {
            tv_name_admin.setText("Chào mừng bạn đến với chúng tôi")
        }
    }
}