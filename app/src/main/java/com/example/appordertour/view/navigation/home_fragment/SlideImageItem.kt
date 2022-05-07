package com.example.appordertour.view.navigation.home_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.squareup.picasso.Picasso

class SlideImageItem(
    private val mListImageSlide: MutableList<SlideImage>
) : RecyclerView.Adapter<SlideImageItem.SlideImageViewHolder>() {


    class SlideImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgageView: ImageView = itemView.findViewById(R.id.imageSlideHome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideImageViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slide_image_home, parent, false)
        return SlideImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideImageViewHolder, position: Int) {
        val imageSlide: SlideImage = mListImageSlide[position]

        if (imageSlide == null) {
            return
        }
        Picasso.get().load(imageSlide.resourceImg).into(holder.imgageView)
    }

    override fun getItemCount(): Int {
        if (mListImageSlide != null) {
            return mListImageSlide.size
        }
        return 0
    }
}

data class SlideImage(var resourceImg: String)
