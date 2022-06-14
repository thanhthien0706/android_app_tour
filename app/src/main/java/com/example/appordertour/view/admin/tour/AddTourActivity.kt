package com.example.appordertour.view.admin.tour

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.databinding.ActivityAddTourBinding
import com.example.appordertour.model.CategoryTour
import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AddTourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTourBinding

    private var listDataCategoryTour = mutableListOf<CategoryTour>()

    val REQUEST_CODE: Int = 100
    val REQUEST_CODE_IMAGE_LIST_TOUR: Int = 200
    private val mTourService = TourService()
    private val mFirebase = Firebase()

    private var filePath: Uri? = null
    private var listFilePathImageTour: MutableList<Uri> = mutableListOf()
    private lateinit var categoryTourId: String
    private lateinit var locationTour: String
    private var listStoppoint = mutableListOf<TouristStopover>()
    private lateinit var statusTour: String
    private var startDate: Long = 0
    private var endDate: Long = 0

    val provinceList: ArrayList<String> = arrayListOf(
        "Hồ Chí Minh",
        "Hà Nội",
        "Đà Nẵng",
        "Bình Dương",
        "Đồng Nai",
        "Khánh Hòa",
        "Hải Phòng",
        "Long An",
        "Quảng Nam",
        "Bà Rịa Vũng Tàu",
        "Đắk Lắk",
        "Cần Thơ",
        "Bình Thuận  ",
        "Lâm Đồng",
        "Thừa Thiên Huế",
        "Kiên Giang",
        "Bắc Ninh",
        "Quảng Ninh",
        "Thanh Hóa",
        "Nghệ An",
        "Hải Dương",
        "Gia Lai",
        "Bình Phước",
        "Hưng Yên",
        "Bình Định",
        "Tiền Giang",
        "Thái Bình",
        "Bắc Giang",
        "Hòa Bình",
        "An Giang",
        "Vĩnh Phúc",
        "Tây Ninh",
        "Thái Nguyên",
        "Lào Cai",
        "Nam Định",
        "Quảng Ngãi",
        "Bến Tre",
        "Đắk Nông",
        "Cà Mau",
        "Vĩnh Long",
        "Ninh Bình",
        "Phú Thọ",
        "Ninh Thuận",
        "Phú Yên",
        "Hà Nam",
        "Hà Tĩnh",
        "Đồng Tháp",
        "Sóc Trăng",
        "Kon Tum",
        "Quảng Bình",
        "Quảng Trị",
        "Trà Vinh",
        "Hậu Giang",
        "Sơn La",
        "Bạc Liêu",
        "Yên Bái",
        "Tuyên Quang",
        "Điện Biên",
        "Lai Châu",
        "Lạng Sơn",
        "Hà Giang",
        "Bắc Kạn",
        "Cao Bằng"
    )
    private var itemsNameCategory: MutableList<String> = mutableListOf()
    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityAddTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onControls()
        onEvents()
    }

    private fun onEvents() {

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChangeAvatarTour.setOnClickListener {
            handleChangeImage()
        }

        initDataDateTour()

        binding.imgBtnAddImageTour.setOnClickListener {
            handleSelectImageTour()
        }

        binding.imgBtnAddStoppoint.setOnClickListener {
            onHandleShowDialog()
        }

        binding.swPublicTourAdd.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                statusTour = "public"
            } else {
                statusTour = "private"
            }
        })

        binding.btnSaveTourAdd.setOnClickListener {
//            Log.d("testDuLie", binding.txtPriceTourAdd.text?.trim().toString())

            mFirebase.uploadImage("image_tour", filePath).addOnSuccessListener {
                it.storage.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val imageMaintour = it.result.toString()
                        var listNewLinKImage = mutableListOf<String>()

                        for (itemImage in listFilePathImageTour) {
                            mFirebase.uploadImage("image_tour", itemImage).addOnSuccessListener {
                                it.storage.downloadUrl.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        listNewLinKImage.add(it.result.toString())
                                        if (listNewLinKImage.size == listFilePathImageTour.size) {
                                            mTourService.createTour(
                                                Tour(
                                                    status = statusTour,
                                                    nameTour = binding.txtNameTourAdd.text?.trim()
                                                        .toString(),
                                                    location = locationTour,
                                                    categoryTour = categoryTourId,
                                                    price = binding.txtPriceTourAdd.text.toString()
                                                        .toLong(),
                                                    startDate = startDate,
                                                    endDate = endDate,
                                                    adults = binding.txtAdultsTourAdd.text.toString()
                                                        .toLong(),
                                                    avater = imageMaintour,
                                                    description = binding.txtDescriptonTourAdd.text?.trim()
                                                        .toString(),
                                                    listImage = listNewLinKImage,
                                                    stoppoint = listStoppoint
                                                )
                                            ) { status ->
                                                if (status) {
                                                    Toast.makeText(
                                                        this,
                                                        "Tạo tour thành công",
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                    finish()

                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Tạo tour thất bại",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }

        }

    }

    private fun onHandleShowDialog() {
        var dialogStoppoint: Dialog = Dialog(this)
        dialogStoppoint.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogStoppoint.setContentView(R.layout.item_add_stoppoint_tour)

        var widow: Window? = dialogStoppoint.window
        if (widow == null) {
            return
        }

        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        widow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var windowAttr: WindowManager.LayoutParams = window.attributes
        windowAttr.gravity = Gravity.CENTER
        window.attributes = windowAttr

        val btnCancelDialog: Button = dialogStoppoint.findViewById(R.id.cancelStoppoint)
        val addStopponit: Button = dialogStoppoint.findViewById(R.id.addStopponit)
        val txt_name_stopponit: TextInputEditText =
            dialogStoppoint.findViewById(R.id.txt_name_stopponit)
        val txt_location_stopponit: TextInputEditText =
            dialogStoppoint.findViewById(R.id.txt_location_stopponit)
        val txt_description_stopponit: TextInputEditText =
            dialogStoppoint.findViewById(R.id.txt_description_stopponit)
        val txt_time_stopponit: TextInputEditText =
            dialogStoppoint.findViewById(R.id.txt_time_stopponit)


        btnCancelDialog.setOnClickListener {
            dialogStoppoint.dismiss()
        }

        addStopponit.setOnClickListener {

            binding.rcvListStoppoint.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            listStoppoint.add(
                TouristStopover(
                    name = txt_name_stopponit.text.toString(),
                    descripton = txt_description_stopponit.text.toString(),
                    location = txt_location_stopponit.text.toString(),
                    time = txt_time_stopponit.text.toString()
                )
            )

            binding.rcvListStoppoint.adapter = StoppointAdapter(listStoppoint)
            dialogStoppoint.dismiss()
        }

        dialogStoppoint.show()
    }

    private fun handleSelectImageTour() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_LIST_TOUR)
    }

    private fun initDataDateTour() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày")
                .build()

        binding.txtStartDayTourAdd.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày sinh")
                    .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener {
                startDate = it
                binding.txtStartDayTourAdd.setText(datePicker.headerText)
            }
        }


        binding.txtEndDayTourAdd.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày sinh")
                    .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener {
                endDate = it
                binding.txtEndDayTourAdd.setText(datePicker.headerText)
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
                Glide.with(this).load(data.data!!).into(binding.imgAvatarTour)
            }
        }


        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_LIST_TOUR) {
            if (data != null) {
                binding.rcvImageTour.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

                listFilePathImageTour.add(data.data!!)
                var imageRef: MutableList<String> = mutableListOf()
                for (item in listFilePathImageTour) {
                    imageRef.add(
                        item.toString()
                    )
                }

                binding.rcvImageTour.adapter = ImageTourAdapter(imageRef)
//                Glide.with(this).load(data.data!!).into(binding.imgAvatarTour)
            }
        }
    }

    private fun onControls() {
        initDataCategory()
        intiDataLocation()
    }


    private fun intiDataLocation() {
        binding.selectLocationTour.setAdapter(
            ArrayAdapter(this, R.layout.list_string_item_category, provinceList)
        )
        binding.selectLocationTour.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var location: String = p0?.getItemAtPosition(p2).toString()
                locationTour = location
            }
        })
    }

    private fun initDataCategory() {

        mTourService.getAllCategoryTour().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result) {
                    listDataCategoryTour.add(
                        CategoryTour(
                            document.id.toString().trim(),
                            document.get("resourceImage").toString(),
                            document.get("description").toString(),
                            document.get("name").toString()
                        )
                    )
                    itemsNameCategory.add(
                        document.get("name").toString()
                    )
                }

                adapterItems =
                    ArrayAdapter(this, R.layout.list_string_item_category, itemsNameCategory)
                binding.selectCategoryTour.setAdapter(adapterItems)
                binding.selectCategoryTour.setOnItemClickListener(
                    object : AdapterView.OnItemClickListener {
                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            var item: String = p0?.getItemAtPosition(p2).toString()

                            for (itemCategory in listDataCategoryTour) {
                                if (itemCategory.name.equals(item)) {
                                    categoryTourId = itemCategory.id.toString()
                                    break
                                }
                            }

                        }

                    }
                )
            }
        }


    }
}