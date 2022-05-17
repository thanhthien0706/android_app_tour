package com.example.appordertour.view.detail_tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.Comment
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.admin.chat_admin.ItemChatAdapter
import de.hdodenhof.circleimageview.CircleImageView

class ItemCommentAdapter(private var mListComment: List<Comment>) :
    RecyclerView.Adapter<ItemCommentAdapter.ItemCommentViewHolder>() {

    private lateinit var context: android.content.Context
    private val mFirebase = Firebase()

    class ItemCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentAvatar = itemView.findViewById<CircleImageView>(R.id.commentAvatar)
        val commentName = itemView.findViewById<TextView>(R.id.commentName)
        val commentContent = itemView.findViewById<TextView>(R.id.commentContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentViewHolder {
        context = parent.context

        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment, parent, false)
        return ItemCommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCommentViewHolder, position: Int) {
        val comment = mListComment[position]

        mFirebase.getUserData(comment.idUser).addOnCompleteListener {
            if (it.isSuccessful) {

                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]

                    Glide.with(context).load(dataUser.get("avatar")).into(holder.commentAvatar)
                    holder.commentName.setText(dataUser.get("userName").toString())
                }
            }
        }
        holder.commentContent.setText(comment.content)
    }

    override fun getItemCount(): Int {
        return mListComment.size
    }
}