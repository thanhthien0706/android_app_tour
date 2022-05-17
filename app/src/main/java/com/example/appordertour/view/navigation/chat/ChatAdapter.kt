package com.example.appordertour.view.navigation.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Messenger
import com.example.appordertour.model.MessengerDetail
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.admin.MainAdminActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class ChatAdapter(private val mListChat: List<MessengerDetail>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ME_CHAT = 1
        const val TYPE_USER_CHAT = 2
    }

    private var auth: FirebaseAuth = com.google.firebase.ktx.Firebase.auth
    private val mFirebase = Firebase()

    class ChatMeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMeChat: TextView = itemView.findViewById(R.id.tv_me_chat)
    }

    class ChatUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserChat: TextView = itemView.findViewById(R.id.tv_user_chat)
    }

    override fun getItemViewType(position: Int): Int {
        val mess: MessengerDetail = mListChat[position]
//        var roleView: Int = 0


        if (auth.currentUser?.uid == mess.idSender) {
            return TYPE_ME_CHAT
        } else {
            return TYPE_USER_CHAT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (TYPE_ME_CHAT == viewType) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_me_chat, parent, false)
            return ChatMeViewHolder(view)
        } else if (TYPE_USER_CHAT == viewType) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_chat, parent, false)
            return ChatUserViewHolder(view)
        }
        return throw IllegalArgumentException("Unsupported layout")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mess: MessengerDetail = mListChat[position]
        if (mess == null) {
            return;
        }

        if (TYPE_ME_CHAT == holder.itemViewType) {

            val meChatViewHolder: ChatMeViewHolder = holder as ChatMeViewHolder
            meChatViewHolder.tvMeChat.setText(mess.content)

        } else if (TYPE_USER_CHAT == holder.itemViewType) {

            val userChatViewHolder: ChatUserViewHolder = holder as ChatUserViewHolder
            userChatViewHolder.tvUserChat.setText(mess.content)

        }
    }

    override fun getItemCount(): Int {
        if (mListChat != null) {
            return mListChat.size
        }
        return 0
    }
}