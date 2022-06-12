package com.example.appordertour.view.admin.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.squareup.picasso.Picasso

class ImageTourAdapter(private val mListImageTour: MutableList<String>) :
    RecyclerView.Adapter<ImageTourAdapter.ImageTourViewHolder>() {
    private lateinit var context: android.content.Context

    class ImageTourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_tour_add: ImageView = itemView.findViewById(R.id.img_tour_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageTourViewHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tour_add, parent, false)
        return ImageTourViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageTourViewHolder, position: Int) {
        val imageLink: String = mListImageTour[position]

        Picasso.get().load(imageLink).into(holder.img_tour_add)
    }

    override fun getItemCount(): Int {
        return mListImageTour.size
    }
}