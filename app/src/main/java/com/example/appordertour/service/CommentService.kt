package com.example.appordertour.service

import com.example.appordertour.model.Comment
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.*

class CommentService {
    private val database = FirebaseDatabase.getInstance().getReference()

    fun createComment(dataComment: Comment): Task<Void> {
        return database.child("comment").child(UUID.randomUUID().toString()).setValue(dataComment)
    }

    fun getCommentWithidTour(idTour: String): Query {
        return database.child("comment").orderByChild("idTour").equalTo(idTour)
    }

}