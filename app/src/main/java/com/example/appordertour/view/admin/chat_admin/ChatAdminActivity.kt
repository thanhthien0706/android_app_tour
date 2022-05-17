package com.example.appordertour.view.admin.chat_admin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Messenger
import com.example.appordertour.model.MessengerDetail
import com.example.appordertour.service.MessengerAdminService
import com.example.appordertour.view.navigation.chat.ChatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatAdminActivity : AppCompatActivity() {

    private lateinit var btn_back_admin: ImageButton
    private lateinit var rcv_chat_admin: RecyclerView

    private val mMessengerAdmin = MessengerAdminService()
    private var listMess = mutableListOf<Messenger>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_chat_admin)

        addCotrolls()
        addEvents()
    }

    private fun addEvents() {
        btn_back_admin.setOnClickListener {
            finish()
        }
    }

    private fun addCotrolls() {
        btn_back_admin = findViewById(R.id.btn_back_admin)
        rcv_chat_admin = findViewById(R.id.rcv_chat_admin)

        initData()
    }

    private fun initData() {
        rcv_chat_admin.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mMessengerAdmin.getAllMessenger().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMess.clear()

                for (item in snapshot.children) {
                    val data = item.getValue(Messenger::class.java)
                    listMess.add(
                        Messenger(
                            data?.id.toString(),
                            data?.idUser.toString(),
                            data?.createAt
                        )
                    )
                }

                rcv_chat_admin.adapter = ItemChatAdapter(listMess)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}