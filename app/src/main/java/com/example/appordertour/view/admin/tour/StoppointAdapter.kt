package com.example.appordertour.view.admin.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.TouristStopover

class StoppointAdapter(private val mListStoppoint: MutableList<TouristStopover>) :
    RecyclerView.Adapter<StoppointAdapter.StoppointViewHolder>() {

    private lateinit var context: android.content.Context

    class StoppointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_name_stoppoint: TextView = itemView.findViewById(R.id.txt_name_stoppoint)
        val txt_location_stopponit: TextView = itemView.findViewById(R.id.txt_location_stopponit)
        val txt_time_stoppoint: TextView = itemView.findViewById(R.id.txt_time_stoppoint)
        val txt_description_stoppoint: TextView =
            itemView.findViewById(R.id.txt_description_stoppoint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoppointViewHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stoppoint_tour, parent, false)
        return StoppointViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoppointViewHolder, position: Int) {
        val stoppoint: TouristStopover = mListStoppoint[position]

        holder.txt_name_stoppoint.setText(stoppoint.name)
        holder.txt_location_stopponit.setText(stoppoint.location)
        holder.txt_time_stoppoint.setText(stoppoint.time)
        holder.txt_description_stoppoint.setText(stoppoint.descripton)

    }

    override fun getItemCount(): Int {
        return mListStoppoint.size
    }
}