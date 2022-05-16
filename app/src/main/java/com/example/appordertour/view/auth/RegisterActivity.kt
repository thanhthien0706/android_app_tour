package com.example.appordertour.view.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appordertour.databinding.ActivityRegisterBinding
import com.example.appordertour.service.Firebase
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebase: Firebase
    private lateinit var userName: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var pass: TextInputEditText
    private lateinit var comfirm_pass: TextInputEditText

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
        binding.tvSelectLogin.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                )
            )
            finish()
        }

        binding.btnRegister.setOnClickListener {


//            Log.d(
//                "testDuLieu",
//                "${email.text?.trim().toString()} ${
//                    pass.text?.trim().toString()
//                }  ${userName.text?.trim().toString()}"
//            )

            firebase.createNewAccount(
                email.text?.trim().toString(),
                pass.text?.trim().toString(),
                userName.text?.trim().toString()
            ) { status ->
                if (status == true) {
                    startActivity(
                        Intent(application, LoginActivity::class.java)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "register That bai", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onControls() {
        firebase = Firebase()
        userName = binding.txtUserNameRegister
        email = binding.txtEmailRegister
        pass = binding.txtPassLoginRegister
        comfirm_pass = binding.txtPassLoginReRegister
    }
}