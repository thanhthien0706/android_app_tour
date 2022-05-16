package com.example.appordertour.view.navigation.home_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.CategoryTour
import com.squareup.picasso.Picasso

class categoryAdapter(private val mListCategory: MutableList<CategoryTour>) :
    RecyclerView.Adapter<categoryAdapter.categoryViewHolder>() {

    class categoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resourceImage: ImageView = itemView.findViewById(R.id.img_category)
        val tvNameCategory: TextView = itemView.findViewById(R.id.tv_name_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.category_home, parent, false)

        return categoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val category: CategoryTour = mListCategory[position]

        if (category == null) {
            return
        }

//        holder.resourceImage.setImageResource(category.resourceImage)
        Picasso.get().load(category.resourceImage).into(holder.resourceImage)
        holder.tvNameCategory.setText(category.name)
    }

    override fun getItemCount(): Int {
        return mListCategory.size
    }
}

