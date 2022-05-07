package com.example.appordertour.service

import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.security.auth.callback.Callback

class TourService {
    private val db = Firebase.firestore

    /**
     * CREATE TOUR
     */

    fun createTour(
        dataTour: TouristStopover,
        callback: (status: Boolean) -> Unit
    ) {

        db.collection("tour_service").add(dataTour).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.invoke(true)
            } else {
                callback.invoke(false)
            }
        }
    }

    /**
     * GET TOUR
     */

    fun getLimitTour(limit: Long = 4): Task<QuerySnapshot> {
        return db.collection("tour").limit(limit).orderBy("price").get()
    }
}

