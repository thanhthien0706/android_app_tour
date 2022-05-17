package com.example.appordertour.service

import com.example.appordertour.model.MessengerDetail
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.*

class MessengerAdminService {
    private val database = FirebaseDatabase.getInstance().getReference()
    private val firebase = com.example.appordertour.service.Firebase()

    fun getLastMessenger(idMessenger: String?): Query {
        return database.child("messenger_detail").orderByChild("idMessenger")
            .equalTo("idMessenger", idMessenger)
    }

    fun getAllMessenger(): DatabaseReference {
        return database.child("messenger")
    }

    fun addMessengerDetailFormAdmin(
        content: String,
        idMessenger: String?,
        idCurrentUser: String
    ): Task<Void> {

        return database.child("messenger_detail").child(UUID.randomUUID().toString()).setValue(
            MessengerDetail(
                idMessenger = idMessenger,
                content = content,
                idSender = idCurrentUser,
                createAt = Calendar.getInstance().time.time
            )
        )
    }
}