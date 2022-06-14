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
import com.example.appordertour.databinding.ActivityAdminEditTourBinding
import com.example.appordertour.model.CategoryTour
import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import java.util.ArrayList

class AdminEditTourActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminEditTourBinding

    private lateinit var tourData: Tour
    private val db = com.google.firebase.ktx.Firebase.firestore

    private var listDataCategoryTour = mutableListOf<CategoryTour>()

    val REQUEST_CODE: Int = 100
    val REQUEST_CODE_IMAGE_LIST_TOUR: Int = 200
    private val mTourService = TourService()
    private val mFirebase = Firebase()
    private val mBaseCustom = BaseCustom()

    private var filePath: Uri? = null
    private var listFilePathImageTour: MutableList<Uri> = mutableListOf()
    private lateinit var categoryTourId: String
    private lateinit var locationTour: String
    private var listStoppoint = mutableListOf<TouristStopover>()
    private lateinit var statusTour: String
    private var startDate: Long = 0
    private var endDate: Long = 0
    private var newListImagetour = mutableListOf<String>()

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

        binding = ActivityAdminEditTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        tourData = bundle.get("object_tour") as Tour

        onControls()
        onEvents()
    }

    private fun onEvents() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.imgBtnEditStoppoint.setOnClickListener {
            onHandleShowDialog()
        }
        initDataDateTour()
        binding.swPublicEditTour.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked) {
                statusTour = "public"
            } else {
                statusTour = "private"
            }
        })

        binding.btnChangeAvatarEditTour.setOnClickListener {
            handleChangeAvatar()
        }
        binding.imgBtnAddImageEditTour.setOnClickListener {
            handleSelectImageTour()
        }
        binding.btnSaveEditTour.setOnClickListener {
            handleUpdateTour()
        }
    }

    private fun handleUpdateTour() {
        val tourRef = db.collection("tour").document(tourData.id.toString())

        if (filePath != null) {
            mFirebase.uploadImage("image_tour", filePath).addOnSuccessListener {
                it.storage.downloadUrl.addOnCompleteListener {
                    val imageMaintour = it.result.toString()
                    var listNewLinKImage = mutableListOf<String>()

                    if (listFilePathImageTour.size > 0) {
                        for (itemImage in listFilePathImageTour) {
                            mFirebase.uploadImage("image_tour", itemImage).addOnSuccessListener {
                                it.storage.downloadUrl.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        listNewLinKImage.add(it.result.toString())
                                        if (listNewLinKImage.size == listFilePathImageTour.size) {
                                            tourRef.update(
                                                mapOf(
                                                    "status" to statusTour,
                                                    "nameTour" to binding.txtNameEditTour.text.toString(),
                                                    "location" to locationTour,
                                                    "categoryTour" to categoryTourId,
                                                    "price" to binding.txtPriceEditTour.text.toString()
                                                        .toLong(),
                                                    "adults" to binding.txtAdultsEditTour.text.toString()
                                                        .toLong(),
                                                    "startDate" to startDate,
                                                    "endDate" to endDate,
                                                    "description" to binding.txtDescriptonEditTour.text?.trim()
                                                        .toString(),
                                                    "stoppoint" to listStoppoint,
                                                    "listImage" to newListImagetour,
                                                    "avater" to imageMaintour,
                                                )
                                            ).addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "Cập nhật thành công",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    finish()
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Cập nhật không thành công",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        tourRef.update(
                            mapOf(
                                "status" to statusTour,
                                "nameTour" to binding.txtNameEditTour.text.toString(),
                                "location" to locationTour,
                                "categoryTour" to categoryTourId,
                                "price" to binding.txtPriceEditTour.text.toString().toLong(),
                                "adults" to binding.txtAdultsEditTour.text.toString().toLong(),
                                "startDate" to startDate,
                                "endDate" to endDate,
                                "description" to binding.txtDescriptonEditTour.text?.trim()
                                    .toString(),
                                "stoppoint" to listStoppoint,
                                "avater" to imageMaintour,

                                )
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG)
                                    .show()
                                finish()
                            } else {
                                Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
        } else {
            var listNewLinKImage = mutableListOf<String>()

            if (listFilePathImageTour.size > 0) {
                for (itemImage in listFilePathImageTour) {
                    mFirebase.uploadImage("image_tour", itemImage).addOnSuccessListener {
                        it.storage.downloadUrl.addOnCompleteListener {
                            if (it.isSuccessful) {
                                listNewLinKImage.add(it.result.toString())
                                newListImagetour.add(it.result.toString())

                                if (listNewLinKImage.size == listFilePathImageTour.size) {
                                    tourRef.update(
                                        mapOf(
                                            "status" to statusTour,
                                            "nameTour" to binding.txtNameEditTour.text.toString(),
                                            "location" to locationTour,
                                            "categoryTour" to categoryTourId,
                                            "price" to binding.txtPriceEditTour.text.toString()
                                                .toLong(),
                                            "adults" to binding.txtAdultsEditTour.text.toString()
                                                .toLong(),
                                            "startDate" to startDate,
                                            "endDate" to endDate,
                                            "description" to binding.txtDescriptonEditTour.text?.trim()
                                                .toString(),
                                            "stoppoint" to listStoppoint,
                                            "listImage" to newListImagetour,

                                            )
                                    ).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Cập nhật thành công",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Cập nhật không thành công",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                tourRef.update(
                    mapOf(
                        "status" to statusTour,
                        "nameTour" to binding.txtNameEditTour.text.toString(),
                        "location" to locationTour,
                        "categoryTour" to categoryTourId,
                        "price" to binding.txtPriceEditTour.text.toString().toLong(),
                        "adults" to binding.txtAdultsEditTour.text.toString().toLong(),
                        "startDate" to startDate,
                        "endDate" to endDate,
                        "description" to binding.txtDescriptonEditTour.text?.trim()
                            .toString(),
                        "stoppoint" to listStoppoint

                    )
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleSelectImageTour() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_LIST_TOUR)
    }

    private fun handleChangeAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                filePath = data.data!!
                Glide.with(this).load(data.data!!).into(binding.imgAvatarEditTour)
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_LIST_TOUR) {
            if (data != null) {
                binding.rcvImageEditTour.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

                listFilePathImageTour.add(data.data!!)

                newListImagetour.add(
                    data.data.toString()
                )

                binding.rcvImageEditTour.adapter = ImageTourAdapter(newListImagetour)
            }
        }


    }

    private fun onControls() {
        initData()
        for (item in tourData.listImage!!) {
            newListImagetour.add(
                item.toString()
            )
        }

        startDate = tourData.startDate!!
        endDate = tourData.endDate!!
        statusTour = tourData.status.toString()
        locationTour = tourData.location.toString()
        categoryTourId = tourData.categoryTour.toString()
    }

    private fun initData() {
        Glide.with(this).load(tourData.avater).into(binding.imgAvatarEditTour)

        binding.rcvImageEditTour.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        for (item in tourData.listImage!!) {
            newListImagetour.add(
                item.toString()
            )
        }
        binding.rcvImageEditTour.adapter = ImageTourAdapter(newListImagetour)

        binding.selectCategoryEditTour.setText(tourData.categoryTour)
        binding.txtAdultsEditTour.setText(tourData.adults.toString())
        binding.txtNameEditTour.setText(tourData.nameTour)
        binding.txtDescriptonEditTour.setText(tourData.description)
        binding.txtPriceEditTour.setText(tourData.price.toString())
        binding.txtStartDayEditTour.setText(mBaseCustom.convertLongToTime(tourData.startDate))
        binding.txtEndDayEditTour.setText(mBaseCustom.convertLongToTime(tourData.endDate))
        if (tourData.status == "public") {
            binding.swPublicEditTour.isChecked = true
        } else {
            binding.swPublicEditTour.isChecked = false
        }


        binding.rcvListStoppointEditTour.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        for (point in tourData.stoppoint as ArrayList<*>) {
            Log.d("dataDiemDung", point.toString())
            var stopPoint = point as HashMap<String, TouristStopover>

            listStoppoint.add(
                TouristStopover(
                    name = stopPoint.get("name").toString(),
                    descripton = stopPoint.get("descript").toString(),
                    location = stopPoint.get("location").toString(),
                    time = stopPoint.get("time").toString()
                )
            )
        }
        binding.rcvListStoppointEditTour.adapter =
            StoppointAdapter(listStoppoint)

        initDataCategory()
        intiDataLocation()
    }

    private fun initDataDateTour() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày")
                .build()

        binding.txtStartDayEditTour.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày sinh")
                    .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener {
                startDate = it
                binding.txtStartDayEditTour.setText(datePicker.headerText)
            }
        }


        binding.txtEndDayEditTour.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày sinh")
                    .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener {
                endDate = it
                binding.txtEndDayEditTour.setText(datePicker.headerText)
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

            binding.rcvListStoppointEditTour.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            listStoppoint.add(
                TouristStopover(
                    name = txt_name_stopponit.text.toString(),
                    descripton = txt_description_stopponit.text.toString(),
                    location = txt_location_stopponit.text.toString(),
                    time = txt_time_stopponit.text.toString()
                )
            )

            binding.rcvListStoppointEditTour.adapter = StoppointAdapter(listStoppoint)
            dialogStoppoint.dismiss()
        }

        dialogStoppoint.show()
    }

    private fun intiDataLocation() {
        binding.selectLocationEditTour.setText(tourData.location)
        binding.selectLocationEditTour.setAdapter(
            ArrayAdapter(this, R.layout.list_string_item_category, provinceList)
        )
        binding.selectLocationEditTour.setOnItemClickListener(object :
            AdapterView.OnItemClickListener {
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
                    if (document.id.toString() == tourData.categoryTour.toString()) {
                        binding.selectCategoryEditTour.setText(document.get("name").toString())
                    }
                    itemsNameCategory.add(
                        document.get("name").toString()
                    )
                }

                adapterItems =
                    ArrayAdapter(this, R.layout.list_string_item_category, itemsNameCategory)
                binding.selectCategoryEditTour.setAdapter(adapterItems)
                binding.selectCategoryEditTour.setOnItemClickListener(
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