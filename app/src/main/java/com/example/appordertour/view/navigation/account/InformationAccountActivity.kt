package com.example.appordertour.view.navigation.account

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.service.Firebase
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView

class InformationAccountActivity : AppCompatActivity() {
    private lateinit var tlBirthdayAccount: TextInputLayout

    private lateinit var txtNameAccount: TextInputEditText
    private lateinit var txtEmailAccount: TextInputEditText
    private lateinit var txtPhoneAccount: TextInputEditText
    private lateinit var txtBirthdayAccount: TextInputEditText
    private lateinit var txtAddressAccount: TextInputEditText
    private lateinit var radioButtonMen: RadioButton
    private lateinit var radioButtonWomen: RadioButton
    private lateinit var imgAvaterAccount: CircleImageView
    private lateinit var btnChangeAvatar: ImageView
    private lateinit var btnSaveInforAccount: Button
    private lateinit var btnBackAccount: ImageButton

    private var gender: String = ""
    val REQUEST_CODE: Int = 100
    private var filePath: Uri? = null

    private lateinit var mFirebase: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_account)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        addControls()
        addEvents()

    }

    private fun addEvents() {

        btnChangeAvatar.setOnClickListener {
            handleChangeImage()
        }

        btnSaveInforAccount.setOnClickListener {
            handleSaveAccount()
        }

        txtBirthdayAccount.setOnClickListener {
            handleChangeBirthday()
        }

        handleCheckGender()

        btnBackAccount.setOnClickListener { finish() }

    }

    private fun handleCheckGender() {
        radioButtonMen.setOnClickListener {
            gender = "men"
        }

        radioButtonWomen.setOnClickListener {
            gender = "women"
        }
    }

    private fun handleChangeBirthday() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày sinh")
                .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener {
            txtBirthdayAccount.setText(datePicker.headerText)
        }

    }

    private fun handleSaveAccount() {
        mFirebase.updateUser(
            txtEmailAccount.text.toString(),
            txtAddressAccount.text.toString(),
            txtNameAccount.text.toString(),
            txtPhoneAccount.text.toString(),
            txtBirthdayAccount.text.toString(),
            gender,
            filePath
        ) { status ->
            if (status) {

                initDataSetting()
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
            }
        }
    }

    private fun handleChangeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                filePath = data.data!!
                Glide.with(this).load(data.data!!).into(imgAvaterAccount)
            }
        }
    }

    private fun initDataSetting() {
        val id = mFirebase.getCurrentUser()?.uid.toString()

        mFirebase.getUserData(id).addOnCompleteListener {
            if (it.isSuccessful) {
                val dataUser = it.result.documents[0]
                txtNameAccount.setText(dataUser.get("userName").toString())
                txtEmailAccount.setText(dataUser.get("mail").toString())
                txtPhoneAccount.setText(dataUser.get("phone").toString())
                txtBirthdayAccount.setText(dataUser.get("date").toString())
                txtAddressAccount.setText(dataUser.get("address").toString())

                if (dataUser.get("gender").toString()?.equals("men")) {
                    radioButtonMen.isChecked = true
                } else if (dataUser.get("gender").toString()?.equals("women")) {
                    radioButtonWomen.isChecked = true
                }

                Glide.with(this).load(dataUser.get("avatar")).into(imgAvaterAccount)
            }
        }
    }

    private fun addControls() {
        txtNameAccount = findViewById(R.id.txt_name_account)
        txtEmailAccount = findViewById(R.id.txt_email_account)
        txtPhoneAccount = findViewById(R.id.txt_phone_account)
        txtBirthdayAccount = findViewById(R.id.txt_birthday_account)
        txtAddressAccount = findViewById(R.id.txt_address_account)
        radioButtonMen = findViewById(R.id.radio_button_men)
        radioButtonWomen = findViewById(R.id.radio_button_women)
        imgAvaterAccount = findViewById(R.id.img_avater_account)
        btnChangeAvatar = findViewById(R.id.btn_change_avatar)
        btnSaveInforAccount = findViewById(R.id.btn_save_infor_account)
        btnBackAccount = findViewById(R.id.btn_back_account)

        mFirebase = Firebase()

        initDataSetting()

    }
}