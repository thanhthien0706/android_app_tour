package com.example.appordertour.view.navigation.chat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.MessengerDetail
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.MessengerService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var rcvChat: RecyclerView
    private lateinit var txtContentChat: EditText
    private lateinit var btnSendChat: ImageButton
    private lateinit var messAdapter: ChatAdapter
    private var listMess = mutableListOf<MessengerDetail>()
    private lateinit var btnBack: ImageButton

    private lateinit var messService: MessengerService
    private lateinit var firebase: Firebase

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        onControls()
        onEvents()
    }

    private fun onEvents() {
        onHandleSendMess()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun onHandleSendMess() {
        btnSendChat.setOnClickListener {
            val content: String = txtContentChat.text.trim().toString()
            txtContentChat.setText("")
            messService.checkExistMessenger {
                if (it) {
                    messService.addMessengerList("edit").addOnCompleteListener {
                        if (it.isSuccessful) {
                            messService.addMessengerDetail(content)
                        } else {
                            Log.d("checkTonTai", "edit that bai")
                        }


                    }
                } else {
//                khong ton tai trong messenger list
                    messService.addMessengerList("add").addOnCompleteListener {
                        if (it.isSuccessful) {
                            messService.addMessengerDetail(content)
                        } else {
                            Log.d("checkTonTai", "tao that bai")
                        }
                    }
                }
            }
        }
    }

    private fun listenerMessenger() {
        messService.readMessengerDetail().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMess.clear()

                for (item in snapshot.children) {
                    val data = item.getValue(MessengerDetail::class.java)

                    if (data?.idMessenger == "${id}_mess") {
                        var messengerClass: MessengerDetail = MessengerDetail()

                        messengerClass.content = data?.content
                        messengerClass.idSender = data?.idSender
                        messengerClass.createAt = data?.createAt!!
                        messengerClass.idMessenger = data?.idMessenger

                        listMess.add(messengerClass)
                    }
                }

                messAdapter = ChatAdapter(listMess)
                rcvChat.adapter = messAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun onControls() {
        rcvChat = findViewById(R.id.rcv_chat)
        txtContentChat = findViewById(R.id.txt_content_chat)
        btnSendChat = findViewById(R.id.btn_send_chat)
        btnBack = findViewById(R.id.btn_back)

        messService = MessengerService()
        firebase = Firebase()

        id = firebase.getCurrentUser()?.uid.toString()

        initData()
    }

    private fun initData() {
        rcvChat.layoutManager = LinearLayoutManager(this)
        listenerMessenger()
    }

}