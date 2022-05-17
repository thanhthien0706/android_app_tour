package com.example.appordertour.service

import android.util.Log
import android.widget.Toast
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.model.OrderTour
import com.example.appordertour.model.Tour
import com.example.appordertour.model.TouristStopover
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    fun getTourWithId(idTour: String): Task<DocumentSnapshot> {
        return db.collection("tour").document(idTour).get()
    }

    fun getOrderTourWithId(idUser: String): Task<DocumentSnapshot> {
        return db.collection("order_tour").document(idUser).get()
    }

    fun getTourWithCategoryTour(idCategoryTour: String, callback: (listTour: List<Tour>) -> Unit) {
        db.collection("tour").get().addOnCompleteListener {
            if (it.isSuccessful) {
                var listTour = mutableListOf<Tour>()

                for (document in it.result) {
                    if (document.get("categoryTour").toString() == idCategoryTour.trim()) {

                        listTour.add(
                            Tour(
                                id = document.id,
                                status = document.get("status").toString(),
                                nameTour = document.get("nameTour").toString(),
                                location = document.get("location").toString(),
                                categoryTour = document.get("categoryTour").toString(),
                                price = document.get("price") as Long,
                                startDate = document.get("startDate") as Long,
                                endDate = document.get("endDate") as Long,
                                adults = document.get("adults") as Long,
                                avater = document.get("avater").toString(),
                                isPrivate = document.get("isPrivate") as Boolean,
                                description = document.get("description").toString(),
                                listImage = document.get("listImage") as List<String>,
                                stoppoint = document.get("stoppoint") as List<TouristStopover>
                            )
                        )
                    }
                }

                callback.invoke(listTour)
            }
        }
    }

    /**
     * CATEGORY TOUR
     */

    fun getCategoryTour(idCategoryTour: String): Task<DocumentSnapshot> {
        return db.collection("category_tour").document(idCategoryTour).get()
    }

    fun getAllCategoryTour(): Task<QuerySnapshot> {
        return db.collection("category_tour").get()
    }


    /**
     * ADD ORDER TOUR
     */

    fun checkOrderTourExist(idTour: String, idUser: String, callback: (status: Boolean) -> Unit) {
        db.collection("order_tour").document(idUser).get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    var check = false

                    val dataTour = it.result.get("listIdTour") as ArrayList<*>
                    for (document in dataTour) {

                        var dataChange = document as HashMap<String, ItemIdTour>

                        if (dataChange.get("idTour").toString() == idTour
                        ) {
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

            } else {
                callback.invoke(false)
            }
        }


    }

    fun createOrderTour(idTour: String, idUser: String, callback: (status: Boolean) -> Unit) {
        val dataOrderTour = OrderTour(
            idUser, arrayListOf(
                ItemIdTour(idTour, "booking", Calendar.getInstance().time.time)
            )
        )

        db.collection("order_tour").document(idUser).get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    db.collection("order_tour").document(idUser).update(
                        "listIdTour",
                        FieldValue.arrayUnion(
                            ItemIdTour(
                                idTour,
                                "booking",
                                Calendar.getInstance().time.time
                            )
                        )
                    ).addOnCompleteListener {
                        callback.invoke(true)
                    }
                } else {
                    db.collection("order_tour").document(idUser).set(dataOrderTour)
                        .addOnCompleteListener {
                            callback.invoke(true)
                        }
                }
            }
        }


    }

    fun addOrderTour(idTour: String, idUser: String): Task<Void> {
        return db.collection("order_tour").document(idUser).update(
            "listIdTour",
            FieldValue.arrayUnion(ItemIdTour(idTour, "booking", Calendar.getInstance().time.time))
        )
    }

    /**
     * REMOVE TOUR BOOKING
     */

    fun removeBookingTourWithId(idTour: String, createAt: Long, idUser: String): Task<Void> {
        return db.collection("order_tour").document(idUser).update(
            "listIdTour",
            FieldValue.arrayRemove(
                ItemIdTour(idTour, "booking", createAt)
            )
        )
    }

    /**
     * UPDATE TOUR BOOKING
     */

    fun updateTourBookingWithId(
        idUser: String,
        newArrayBookingTour: MutableList<ItemIdTour>
    ): Task<Void> {
        return db.collection("order_tour").document(idUser)
            .update("listIdTour", newArrayBookingTour)
    }
}

