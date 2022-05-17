package com.example.appordertour.view.navigation.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.service.Firebase
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.navigation.chat.ChatActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AccountFragment : Fragment {
    constructor()

    private lateinit var mView: View
    private lateinit var btnSeenAccount: LinearLayout
    private lateinit var btnSeenNotify: LinearLayout
    private lateinit var btnSeenFavoriteTour: LinearLayout
    private lateinit var btn_tour_account: LinearLayout
    private lateinit var btnLogout: Button
    private lateinit var userAvatar: CircleImageView
    private lateinit var tvNameAccountFragment: TextView

    private lateinit var mFirebase: Firebase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_account, container, false)

        addControls()
        addEvents()

        return mView
    }

    private fun addEvents() {

        btnSeenAccount.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    InformationAccountActivity::class.java
                )
            )
        }

        btn_tour_account.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    MyTourActivity::class.java
                )
            )
        }

        btnSeenNotify.setOnClickListener {
        }

        btnSeenFavoriteTour.setOnClickListener {
        }

        btnLogout.setOnClickListener {
            mFirebase.signOut()
            startActivity(
                Intent(activity, MainActivity::class.java)
            )
        }

    }

    private fun addControls() {
        btnSeenAccount = mView.findViewById(R.id.btn_seen_account)
        btnSeenNotify = mView.findViewById(R.id.btn_seen_notify)
        btnSeenFavoriteTour = mView.findViewById(R.id.btn_seen_favorite_tour)
        btn_tour_account = mView.findViewById(R.id.btn_tour_account)
        btnLogout = mView.findViewById(R.id.btn_logout)
        userAvatar = mView.findViewById(R.id.userAvatar)
        tvNameAccountFragment = mView.findViewById(R.id.tv_name_account_fragment)

        mFirebase = Firebase()

        initDataStart()

    }

    private fun initDataStart() {
        mFirebase.getUserData(
            mFirebase.getCurrentUser()?.uid.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                if (!it.result.isEmpty) {
                    val dataUser = it.result.documents[0]
                    Picasso.get().load(dataUser.get("avatar").toString()).into(userAvatar)
                    tvNameAccountFragment.setText(dataUser.get("userName").toString())
                } else {
                    btnSeenAccount.visibility = View.GONE
                }
            }
        }
    }
}