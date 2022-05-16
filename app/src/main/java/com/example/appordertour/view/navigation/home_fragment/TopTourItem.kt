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
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.util.BaseCustom
import com.example.appordertour.view.MainActivity
import com.example.appordertour.view.detail_tour.ActivitiesDetailTourFragment
import com.example.appordertour.view.detail_tour.DetailTourActivity
import com.example.appordertour.view.detail_tour.ImageDetailTourFragment
import com.example.appordertour.view.detail_tour.OverviewDetailTourFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class TopTourItem(
    private val context: FragmentActivity?,
    private val mListTopTour: List<Tour>,
) : RecyclerView.Adapter<TopTourItem.TopTourViewHolder>() {
    private val baseCustom: BaseCustom = BaseCustom()

    class TopTourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTour: ImageView = itemView.findViewById(R.id.img_tour)
        val tvNameTour: TextView = itemView.findViewById(R.id.tv_name_tour)
        val tvLocationTour: TextView = itemView.findViewById(R.id.tv_location_tour)
        val tvPriceTour: TextView = itemView.findViewById(R.id.tv_price_tour)
        val loItemTour: MaterialCardView = itemView.findViewById(R.id.lo_item_tour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTourViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tour, parent, false)
        return TopTourViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopTourViewHolder, position: Int) {
        val topTour: Tour = mListTopTour[position]

        if (topTour == null) return

        Picasso.get().load(topTour.avater).into(holder.imgTour)
        holder.tvLocationTour.setText(topTour.location)
        holder.tvNameTour.setText(topTour.nameTour)
        holder.tvPriceTour.setText(baseCustom.convertLongtoCurrency(topTour.price))

        holder.loItemTour.setOnClickListener {
            val intent: Intent = Intent(context, DetailTourActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putSerializable("tour_student", topTour)
            bundle.putSerializable("descriptionTour", topTour.description)
            bundle.putSerializable(
                "dateTour",
                "${baseCustom.convertLongToTime(topTour.startDate)} - ${
                    baseCustom.convertLongToTime(topTour.endDate)
                }"
            )
            bundle.putSerializable("ageTour", topTour.adults)
            bundle.putStringArrayList("listImageTour", topTour.listImage as ArrayList<String>?)
            bundle.putString("idTour", topTour.id)

            val overviewDetailTourFragment = OverviewDetailTourFragment.newInstance()
            overviewDetailTourFragment.arguments = bundle

            val imageDetailTourFragment = ImageDetailTourFragment.newInstance()
            imageDetailTourFragment.arguments = bundle

            val activitiesDetailTourFragment = ActivitiesDetailTourFragment.newInstance()
            activitiesDetailTourFragment.arguments = bundle

            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        if (mListTopTour != null) {
            return mListTopTour.size
        }
        return 0
    }
}