package com.example.appordertour.view.admin.chat_admin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.BuyTour
import com.example.appordertour.model.Messenger
import com.example.appordertour.model.MessengerDetail
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.MessengerAdminService
import com.example.appordertour.service.MessengerService
import com.example.appordertour.view.navigation.chat.ChatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class ChatWithUserDetailActivity : AppCompatActivity() {

    private lateinit var btn_back: ImageButton
    private lateinit var img_user_chat: CircleImageView
    private lateinit var tv_user_name_chat: TextView
    private lateinit var rcv_chat_user: RecyclerView
    private lateinit var txt_content_chat_user: EditText
    private lateinit var btn_send_chat_user: ImageButton
    private var listMess = mutableListOf<MessengerDetail>()

    private lateinit var objectUserMessenger: Messenger
    private val mFirebase = Firebase()

    private val messService: MessengerService = MessengerService()
    private val mMessengerAdminService = MessengerAdminService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_chat_with_user_detail)

        addControls()
        addEvents()
    }

    private fun addEvents() {

        btn_back.setOnClickListener {
            finish()
        }

        btn_send_chat_user.setOnClickListener {
            handleSendMess()
        }
    }

    private fun handleSendMess() {
        val content: String = txt_content_chat_user.text.trim().toString()
        txt_content_chat_user.setText("")

        mMessengerAdminService.addMessengerDetailFormAdmin(
            content,
            objectUserMessenger.id,
            mFirebase.getCurrentUser()?.uid.toString()
        )
    }

    private fun addControls() {
        btn_back = findViewById(R.id.btn_back)
        img_user_chat = findViewById(R.id.img_user_chat)
        tv_user_name_chat = findViewById(R.id.tv_user_name_chat)
        rcv_chat_user = findViewById(R.id.rcv_chat_user)
        txt_content_chat_user = findViewById(R.id.txt_content_chat_user)
        btn_send_chat_user = findViewById(R.id.btn_send_chat_user)

        initData()
    }

    private fun initData() {
        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }

        objectUserMessenger = bundle.get("object_messenger") as Messenger

        rcv_chat_user.layoutManager = LinearLayoutManager(this)

        mFirebase.getUserData(objectUserMessenger.idUser).addOnCompleteListener {
            if (it.isSuccessful) {

                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]

                    Glide.with(this).load(dataUser.get("avatar")).into(img_user_chat)
                    tv_user_name_chat.setText(dataUser.get("userName").toString())
                }
            }
        }


        messService.readMessengerDetail().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMess.clear()

                for (item in snapshot.children) {
                    val data = item.getValue(MessengerDetail::class.java)

                    if (data?.idMessenger == objectUserMessenger.id) {
                        var messengerClass: MessengerDetail = MessengerDetail()

                        messengerClass.content = data?.content
                        messengerClass.idSender = data?.idSender
                        messengerClass.createAt = data?.createAt!!
                        messengerClass.idMessenger = data?.idMessenger

                        listMess.add(messengerClass)
                    }
                }

                Log.d("apChatNeBa", listMess.toString())

                rcv_chat_user.adapter = ChatAdapter(listMess)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}