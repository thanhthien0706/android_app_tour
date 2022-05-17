package com.example.appordertour.view.navigation.home_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appordertour.R
import com.example.appordertour.model.CategoryTour
import com.squareup.picasso.Picasso

class categoryAdapter(private val mListCategory: MutableList<CategoryTour>) :
    RecyclerView.Adapter<categoryAdapter.categoryViewHolder>() {

    private lateinit var context: Context

    class categoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resourceImage: ImageView = itemView.findViewById(R.id.img_category)
        val tvNameCategory: TextView = itemView.findViewById(R.id.tv_name_category)
        val loItemCategory: LinearLayout = itemView.findViewById(R.id.lo_item_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        context = parent.context

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.category_home, parent, false)

        return categoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val category: CategoryTour = mListCategory[position]

        if (category == null) {
            return
        }

        Picasso.get().load(category.resourceImage).into(holder.resourceImage)
        holder.tvNameCategory.setText(category.name)

        holder.loItemCategory.setOnClickListener {
            val intent: Intent = Intent(context, AllTourWithCategoryActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putSerializable("object_category", category)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mListCategory.size
    }
}

