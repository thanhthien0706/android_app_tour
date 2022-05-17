package com.example.appordertour.view.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.appordertour.R
import com.example.appordertour.view.MainActivity

class SeenLocationActivity : AppCompatActivity() {

    private lateinit var btn_back_to_home_seen_location: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seen_location)

        btn_back_to_home_seen_location = findViewById(R.id.btn_back_to_home_seen_location)

        btn_back_to_home_seen_location.setOnClickListener {
            finish()
        }
    }
}