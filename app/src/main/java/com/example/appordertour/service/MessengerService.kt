package com.example.appordertour.service

import com.example.appordertour.model.Messenger
import com.example.appordertour.model.MessengerDetail
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.io.IOException
import java.util.*

class MessengerService {
    private val database = FirebaseDatabase.getInstance().getReference()
    private val firebase = com.example.appordertour.service.Firebase()
    val idCurrentUser = firebase.getCurrentUser()?.uid.toString()
    val idNewChat = "${idCurrentUser}_mess"

    fun checkExistMessenger(callback: (status: Boolean) -> Unit) {
        var messengerRef = database.child("messenger")

        messengerRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var check = false
                for (item in task.getResult().children) {
                    if (item.child(idCurrentUser).exists()) {
                        check = true
                    }
                }

                if (check) {
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            } else {
                callback.invoke(false)
            }
        }
    }

    fun addMessengerList(action: String): Task<Void> {
        var messengerRef = database.child("messenger")
        if (action == "edit") {
            return messengerRef.child(idNewChat).child("createAt")
                .setValue(Calendar.getInstance().time.time)
        } else if (action == "add") {
            return messengerRef.child(idNewChat).setValue(
                Messenger(
                    id = idNewChat,
                    idUser = idCurrentUser,
                    createAt = Calendar.getInstance().time.time
                )
            )
        }
        return throw IOException()
    }

    fun addMessengerDetail(content: String): Task<Void> {

        var messengerRef =
            database.child("messenger_detail").child(UUID.randomUUID().toString()).setValue(
                MessengerDetail(
                    idMessenger = idNewChat,
                    content = content,
                    idSender = idCurrentUser,
                    createAt = Calendar.getInstance().time.time
                )
            )

        return messengerRef
    }

    fun readMessengerDetail(): Query {
        return database.child("messenger_detail").orderByChild("createAt").limitToFirst(30)
    }

}