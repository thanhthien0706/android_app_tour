package com.example.appordertour.view.detail_tour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Comment
import com.example.appordertour.model.MessengerDetail
import com.example.appordertour.service.CommentService
import com.example.appordertour.view.navigation.chat.ChatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReviewDetailTourFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var tv_add_comment: TextView
    private lateinit var rcv_comment_tour: RecyclerView
    private var listComment = mutableListOf<Comment>()

    private val mCommentService = CommentService()

    companion object {
        val ourInstance = OverviewDetailTourFragment()

        fun newInstance(): OverviewDetailTourFragment {
            return ourInstance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_review_detail_tour, container, false)

        addControlls()
        addEvents()

        return mView
    }

    private fun addEvents() {
        tv_add_comment.setOnClickListener {
            val intent: Intent = Intent(context, CommentActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putString("idTourReview", ourInstance.arguments?.getString("idTour").toString())
            intent.putExtras(bundle)

            startActivity(
                intent
            )
        }
    }

    private fun addControlls() {
        tv_add_comment = mView.findViewById(R.id.tv_add_comment)
        rcv_comment_tour = mView.findViewById(R.id.rcv_comment_tour)

        initData()
    }

    private fun initData() {
        rcv_comment_tour.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        mCommentService.getCommentWithidTour(ourInstance.arguments?.getString("idTour").toString())
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    listComment.clear()

                    for (item in snapshot.children) {
                        val data = item.getValue(Comment::class.java)
                        if (data != null) {
                            listComment.add(data)
                        }

                    }


                    rcv_comment_tour.adapter = ItemCommentAdapter(listComment)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}