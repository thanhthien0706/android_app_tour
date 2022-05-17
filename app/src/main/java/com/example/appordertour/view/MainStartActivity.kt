package com.example.appordertour.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appordertour.R
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.admin.MainAdminActivity

class MainStartActivity : AppCompatActivity() {
    private var mFirebase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addCotrols()
    }

    private fun addCotrols() {
        checkInitData()
    }

    private fun checkInitData() {
        if (mFirebase.checkLogin()) {
            mFirebase.getUserData(mFirebase.getCurrentUser()?.uid.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (!it.result.isEmpty) {
                            val dataUser = it.result.documents[0]
                            if (dataUser.get("permission").toString() == "user") {
                                startActivity(
                                    Intent(this, MainActivity::class.java)
                                )
                                finish()
                            } else if (dataUser.get("permission").toString() == "admin") {
                                startActivity(
                                    Intent(this, MainAdminActivity::class.java)
                                )
                                finish()
                            }
                        }
                    }
                }
        } else {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}