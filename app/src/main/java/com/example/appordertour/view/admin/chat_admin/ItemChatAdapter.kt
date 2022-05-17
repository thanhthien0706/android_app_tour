package com.example.appordertour.view.admin.chat_admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.Messenger
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.MessengerAdminService
import com.example.appordertour.view.detail_tour.DetailTourActivity
import com.example.appordertour.view.navigation.chat.ChatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import de.hdodenhof.circleimageview.CircleImageView

class ItemChatAdapter(private val mListMessenger: List<Messenger>) :
    RecyclerView.Adapter<ItemChatAdapter.ItemChatViewHolder>() {

    private lateinit var context: android.content.Context
    private val mFirebase = Firebase()
    private val mMessengerAdmin = MessengerAdminService()

    class ItemChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_user_chat = itemView.findViewById<CircleImageView>(R.id.img_user_chat)
        val tv_name_user_chat = itemView.findViewById<TextView>(R.id.tv_name_user_chat)

        //        val tv_mess_user_chat = itemView.findViewById<TextView>(R.id.tv_mess_user_chat)
        val layout_item_chat_admin = itemView.findViewById<CardView>(R.id.layout_item_chat_admin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemChatViewHolder {
        context = parent.context

        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_chat__admin, parent, false)
        return ItemChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemChatViewHolder, position: Int) {
        val messenger: Messenger = mListMessenger[position]
        mFirebase.getUserData(messenger.idUser).addOnCompleteListener {
            if (it.isSuccessful) {

                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]

                    Glide.with(context).load(dataUser.get("avatar")).into(holder.img_user_chat)
                    holder.tv_name_user_chat.setText(dataUser.get("userName").toString())
                }
            }
        }

        mMessengerAdmin.getLastMessenger(messenger.id)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("chao_du_lie", snapshot.toString())
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        holder.layout_item_chat_admin.setOnClickListener {
            val intent: Intent = Intent(context, ChatWithUserDetailActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putSerializable("object_messenger", messenger)

            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mListMessenger.size
    }
}