package com.example.appordertour.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appordertour.R
import com.example.appordertour.databinding.ActivityMainBinding
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.auth.LoginActivity
import com.example.appordertour.view.auth.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firebase: Firebase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_main)

        firebase = Firebase()


    }

    override fun onStart() {
        super.onStart()
        firebase.signOut()

        if (firebase.checkLogin() == false) {
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                )
            )
            return
        } else {
            startActivity(
                Intent(applicationContext, RegisterActivity::class.java)
            )
            return
        }
    }
}