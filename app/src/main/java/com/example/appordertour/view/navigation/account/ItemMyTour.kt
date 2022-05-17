package com.example.appordertour.view.navigation.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.BuyTour
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.example.appordertour.view.navigation.booking.DetailBookingTourActivity
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.Days

class ItemMyTour(private val mListBookingTour: MutableList<BuyTour>) :
    RecyclerView.Adapter<ItemMyTour.BookingTourViewHolder>() {

    private val mBaseCustom = BaseCustom()
    private val mTourService = TourService()
    private val mFirebase = Firebase()
    private lateinit var context: Context

    class BookingTourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTimeCurrentBookingTour: TextView =
            itemView.findViewById(R.id.tv_time_current_my_tour)

        val imgBookingTour: ImageView = itemView.findViewById(R.id.img_my_tour)
        val tvNameBookingTour: TextView = itemView.findViewById(R.id.tv_name_my_tour)
        val tvDatebookingTour: TextView = itemView.findViewById(R.id.tv_date_my_tour)
        val tvpriceBookingTour: TextView = itemView.findViewById(R.id.tv_price_my_tour)
        val lo_item_my_tour: MaterialCardView = itemView.findViewById(R.id.lo_item_my_tour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingTourViewHolder {
        context = parent.context

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_tour, parent, false)
        return BookingTourViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingTourViewHolder, position: Int) {
        val myTour: BuyTour = mListBookingTour[position]

        holder.tvTimeCurrentBookingTour.setText(mBaseCustom.convertLongToTime(myTour.createAt))

        mTourService.getTourWithId(myTour.idTour).addOnCompleteListener {
            if (it.isSuccessful) {
                Picasso.get().load(it.result.get("avater").toString()).into(holder.imgBookingTour)
                holder.tvNameBookingTour.setText(it.result.get("nameTour").toString())
                holder.tvDatebookingTour.setText(
                    "${
                        Days.daysBetween(
                            DateTime(it.result.get("endDate")),
                            DateTime(it.result.get("startDate"))
                        ).days.toString()
                    } ngày"
                )
                holder.tvpriceBookingTour.setText(mBaseCustom.convertLongtoCurrency(it.result.get("price") as Long?))
            }
        }

        holder.lo_item_my_tour.setOnClickListener {
            val intent: Intent = Intent(context, DetailMyTourActivity::class.java)
            val bundle: Bundle = Bundle()

            bundle.putSerializable("object_my_tour", myTour)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mListBookingTour.size
    }
}

//data class dataBooking(
//    var
//)