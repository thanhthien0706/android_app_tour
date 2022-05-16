package com.example.appordertour.service

import android.net.Uri
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*


class Firebase {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val storageRef =
        FirebaseStorage.getInstance().getReference()

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

    fun getUserData(id: String): Task<QuerySnapshot> {
        return db.collection("users").whereEqualTo("id", id).get()
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
            } else {
                callback.invoke(false)
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
                db.collection("users").document(idUser).set(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        callback.invoke(true)
                    } else {
                        callback.invoke(false)
                    }
                }
            }
        }
    }


    //    [ Update data user ]
    fun updateUser(
        email: String,
        address: String,
        user_namne: String,
        phone: String,
        birthday: String,
        gender: String,
        filePath: Uri?,
        callback: (status: Boolean) -> Unit
    ) {
        if (filePath != null) {
            uploadImage("user", filePath).addOnSuccessListener {
                it.storage.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection("users").document(getCurrentUser()?.uid.toString()).update(
                            mapOf(
                                "userName" to user_namne,
                                "mail" to email,
                                "phone" to phone,
                                "gender" to gender,
                                "avatar" to it.result.toString(),
                                "date" to birthday,
                                "address" to address,
                            )
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    callback.invoke(true)
                                } else {
                                    callback.invoke(false)
                                }
                            }
                    }
                }
            }
        } else {
            db.collection("users").document(getCurrentUser()?.uid.toString()).update(
                mapOf(
                    "userName" to user_namne,
                    "mail" to email,
                    "phone" to phone,
                    "gender" to gender,
                    "date" to birthday,
                    "address" to address,
                )
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        callback.invoke(true)
                    } else {
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

    fun uploadImage(collection: String, filImg: Uri): UploadTask {
        if (filImg != null) {
            val ref = storageRef.child("/$collection/" + UUID.randomUUID())
            return ref.putFile(filImg)
        }
        return throw IOException()
    }

}
