package com.example.appordertour.view.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appordertour.databinding.ActivityLoginBinding
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.admin.MainAdminActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebase: Firebase

    private lateinit var tv_select_register: TextView
    private lateinit var txt_email_login: TextInputEditText
    private lateinit var txt_pass_login: TextInputEditText

    private val mFirebase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onControls()
        onEvents()
    }

    private fun onEvents() {
        binding.tvSelectRegister.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    RegisterActivity::class.java
                )
            )
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val valEmail = txt_email_login.text?.trim().toString()
            val valPass = txt_pass_login.text?.trim().toString()

            firebase.signIn(valEmail, valPass) { status ->
                if (status) {
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
                    Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_LONG).show()
                }
            }

        }


    }

    private fun onControls() {
        firebase = Firebase()
        txt_email_login = binding.txtEmailLogin
        txt_pass_login = binding.txtPassLogin
    }
}