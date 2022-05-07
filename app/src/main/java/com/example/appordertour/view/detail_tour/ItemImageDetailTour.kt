package com.example.appordertour.view.detail_tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ItemImageDetailTour(private val mListImageDetail: ArrayList<String>) :
    RecyclerView.Adapter<ItemImageDetailTour.ImageDetailViewHolder>() {

    class ImageDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTour: ImageView = itemView.findViewById(R.id.img_detail_item_tour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDetailViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_tour_detail, parent, false)
        return ImageDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageDetailViewHolder, position: Int) {
        val imageTour: String = mListImageDetail[position]
        Picasso.get().load(imageTour).into(holder.imgTour)
    }

    override fun getItemCount(): Int {
        return mListImageDetail.size
    }

}

data class ImageDetail(val resourceImg: String)