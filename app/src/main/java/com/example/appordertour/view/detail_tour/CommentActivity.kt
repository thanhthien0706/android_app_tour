package com.example.appordertour.view.detail_tour

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.appordertour.R
import com.example.appordertour.databinding.ActivityCommentBinding
import com.example.appordertour.databinding.ActivityLoginBinding
import com.example.appordertour.model.Comment
import com.example.appordertour.model.Tour
import com.example.appordertour.service.CommentService
import com.example.appordertour.service.Firebase
import java.util.*

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var idTour: String

    private val mCommentService = CommentService()
    private val mFireBase = Firebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addControlls()
        addEvents()
    }

    private fun addControlls() {
        initData()
    }

    private fun initData() {
        val bundle: Bundle = intent.extras!!
        if (bundle == null) {
            return
        }
        idTour = bundle.getString("idTourReview") as String
    }

    private fun addEvents() {
        binding.sendReviewBtn.setOnClickListener {
            handleComments()
        }
    }

    private fun handleComments() {
        val content = binding.editTourRatingContent.text.toString()

        mCommentService.createComment(
            Comment(
                idUser = mFireBase.getCurrentUser()?.uid.toString(),
                idTour = idTour,
                idParent = "",
                content = content,
                createAt = Calendar.getInstance().time.time
            )
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                finish()
            }
        }
    }
}