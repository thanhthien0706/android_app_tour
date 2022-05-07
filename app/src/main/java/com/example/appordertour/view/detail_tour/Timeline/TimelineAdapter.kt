package com.example.appordertour.view.detail_tour.Timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.TouristStopover
import com.example.appordertour.view.navigation.chat.ChatAdapter

class TimelineAdapter(private val mListStoppoint: List<TouristStopover>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_START_TIMELINE = 1
        const val TYPE_MIDDLE_TIMELINE = 2
        const val TYPE_END_TIMELINE = 3
    }

    class TimelinStartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeStart: TextView = itemView.findViewById(R.id.tv_time_timeline_first)
        val nameStart: TextView = itemView.findViewById(R.id.tv_name_timeline_first)
        val descriptionStart: TextView = itemView.findViewById(R.id.tv_desription_timeline_first)
        val locationStart: TextView = itemView.findViewById(R.id.tv_location_timeline_first)
    }

    class TimelinEndViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeEnd: TextView = itemView.findViewById(R.id.tv_time_timeline_end)
        val nameEnd: TextView = itemView.findViewById(R.id.tv_name_timeline_end)
        val descriptionEnd: TextView = itemView.findViewById(R.id.tv_desription_timeline_end)
        val locationEnd: TextView = itemView.findViewById(R.id.tv_location_timeline_end)
    }

    class TimelinMiddleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeMiddle: TextView = itemView.findViewById(R.id.tv_time_timeline_middle)
        val nameMiddle: TextView = itemView.findViewById(R.id.tv_name_timeline_middle)
        val descriptionMiddle: TextView = itemView.findViewById(R.id.tv_description_timeline_middle)
        val locationMiddle: TextView = itemView.findViewById(R.id.tv_location_timeline_middle)
    }

    override fun getItemViewType(position: Int): Int {

        if (position == 0) {
            return TYPE_START_TIMELINE
        } else if (position == mListStoppoint.size - 1) {
            return TYPE_END_TIMELINE
        } else {
            return TYPE_MIDDLE_TIMELINE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (TYPE_START_TIMELINE == viewType) {

            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_firt_timeline, parent, false)
            return TimelinStartViewHolder(view)

        } else if (TYPE_END_TIMELINE == viewType) {

            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_end_timeline, parent, false)
            return TimelinEndViewHolder(view)

        } else if (TYPE_MIDDLE_TIMELINE == viewType) {

            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_middle_timeline, parent, false)
            return TimelinMiddleViewHolder(view)
        }
        return throw IllegalArgumentException("Unsupported layout")

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stoppoint: TouristStopover = mListStoppoint[position]

        if (TYPE_START_TIMELINE == holder.itemViewType) {
            val startTimeLineViewHolder: TimelinStartViewHolder = holder as TimelinStartViewHolder

            startTimeLineViewHolder.timeStart.setText(stoppoint.time)
            startTimeLineViewHolder.nameStart.setText(stoppoint.name)
            startTimeLineViewHolder.descriptionStart.setText(stoppoint.descripton)
            startTimeLineViewHolder.locationStart.setText(stoppoint.location)
        } else if (TYPE_END_TIMELINE == holder.itemViewType) {
            val endTimeLineViewHolder: TimelinEndViewHolder = holder as TimelinEndViewHolder

            endTimeLineViewHolder.timeEnd.setText(stoppoint.time)
            endTimeLineViewHolder.nameEnd.setText(stoppoint.name)
            endTimeLineViewHolder.descriptionEnd.setText(stoppoint.descripton)
            endTimeLineViewHolder.locationEnd.setText(stoppoint.location)
        } else if (TYPE_MIDDLE_TIMELINE == holder.itemViewType) {
            val middleTimeLineViewHolder: TimelinMiddleViewHolder =
                holder as TimelinMiddleViewHolder

            middleTimeLineViewHolder.timeMiddle.setText(stoppoint.time)
            middleTimeLineViewHolder.nameMiddle.setText(stoppoint.name)
            middleTimeLineViewHolder.descriptionMiddle.setText(stoppoint.descripton)
            middleTimeLineViewHolder.locationMiddle.setText(stoppoint.location)
        }
    }

    override fun getItemCount(): Int {
        if (mListStoppoint != null) {
            return mListStoppoint.size
        }
        return 0
    }
}