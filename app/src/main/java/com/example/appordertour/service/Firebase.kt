package com.example.appordertour.service

import android.util.Log
import com.example.appordertour.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firebase {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private lateinit var email_user: String
    private lateinit var password_user: String

    //    [check login]
    fun checkLogin(): Boolean {
        val currentUser = auth.currentUser

        Log.d("Kiem_tra_user", currentUser.toString())
        if (currentUser == null) {
            return false
        }
        return true
    }

    //    [create new Account in authen]
    fun createNewAccount(
        email: String,
        password: String,
        user_namne: String,
        callback: (status: Boolean) -> Unit
    ) {
        email_user = email
        password_user = password

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                createNewUser(user_namne) { status ->
                    if (status == true) {
                        callback.invoke(true)
                    }
                }
            }
        }.addOnFailureListener {
            callback.invoke(false)
        }

    }

    //    [Create new  user in firestore]
    fun createNewUser(name_user: String, callback: (status: Boolean) -> Unit) {

        if (email_user != null && password_user != null) {
            val user = User(userName = name_user, mail = email_user)

            db.collection("users").add(user).addOnSuccessListener {
                callback.invoke(true)
            }.addOnFailureListener {
                callback.invoke(false)
            }
        }
    }

    //    [Logout]
    fun signOut() {
        auth.signOut()
    }

}
