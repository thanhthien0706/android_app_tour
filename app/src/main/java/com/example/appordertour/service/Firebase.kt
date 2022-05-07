package com.example.appordertour.service

import android.util.Log
import com.example.appordertour.model.User
import com.example.appordertour.view.navigation.home_fragment.SlideImageItem
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Firebase {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private lateinit var email_user: String
    private lateinit var password_user: String

    /**
     * SERVICE AUTH
     */
    //    [check login]
    fun checkLogin(): Boolean {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            return false
        }
        return true
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
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

        val idUser = auth.currentUser?.uid

        if (email_user != null && password_user != null) {
            val user = User(id = idUser, userName = name_user, mail = email_user)

            if (idUser != null) {
                db.collection("users").document(idUser).set(user).addOnSuccessListener {
                    callback.invoke(true)
                }.addOnFailureListener {
                    callback.invoke(false)
                }
            }
        }
    }

    //    [ SIGNIN  ]
    fun signIn(email: String, password: String, callback: (status: Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(true)
            }
        }.addOnFailureListener {
            callback.invoke(false)
        }
    }

    //    [Logout]
    fun signOut() {
        auth.signOut()
    }

    /**
     * SERVICE ...
     */

//    GET LIST IMAGE SLIDE
    fun getSlideImage(): Task<QuerySnapshot> {
        return db.collection("image_slide_location").get()
    }

}
