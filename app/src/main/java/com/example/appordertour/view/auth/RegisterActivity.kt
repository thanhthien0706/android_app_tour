package com.example.appordertour.view.auth

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appordertour.databinding.ActivityRegisterBinding
import com.example.appordertour.service.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebase: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onControls()
        onEvents()
    }

    private fun onEvents() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.txtUserNameRegister.text.toString()
            val email = binding.txtEmailRegister.text.toString()
            val pass = binding.txtPassLoginRegister.text.toString()

            firebase.createNewAccount(email, pass, userName) { status ->
                if (status == true) {
                    Toast.makeText(this, "register thanh cong", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "register That bai", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onControls() {
        firebase = Firebase()
    }
}