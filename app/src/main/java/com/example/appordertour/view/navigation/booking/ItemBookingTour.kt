package com.example.appordertour.view.navigation.booking

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
import com.example.appordertour.model.ItemIdTour
import com.example.appordertour.service.Firebase
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import org.joda.time.DateTime
import org.joda.time.Days

class ItemBookingTour(private val mListBookingTour: MutableList<ItemIdTour>) :
    RecyclerView.Adapter<ItemBookingTour.BookingTourViewHolder>() {

    private val mBaseCustom = BaseCustom()
    private val mTourService = TourService()
    private val mFirebase = Firebase()
    private lateinit var context: Context

    class BookingTourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTimeCurrentBookingTour: TextView =
            itemView.findViewById(R.id.tv_time_current_booking_tour)

        val imgBookingTour: ImageView = itemView.findViewById(R.id.img_booking_tour)
        val tvNameBookingTour: TextView = itemView.findViewById(R.id.tv_name_booking_tour)
        val tvDatebookingTour: TextView = itemView.findViewById(R.id.tv_date_booking_tour)
        val tvpriceBookingTour: TextView = itemView.findViewById(R.id.tv_price_booking_tour)
        val btnRemoveBooking: ImageButton = itemView.findViewById(R.id.btn_remove_booking)
        val loItemBookingTour: MaterialCardView =
            itemView.findViewById(R.id.lo_item_booking_tour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingTourViewHolder {
        context = parent.context

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tour_booking, parent, false)
        return BookingTourViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingTourViewHolder, position: Int) {
        val bookingTour: ItemIdTour = mListBookingTour[position]

        holder.tvTimeCurrentBookingTour.setText(mBaseCustom.convertLongToTime(bookingTour.createAt))

        mTourService.getTourWithId(bookingTour.idTour).addOnCompleteListener {
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
//                Log.d("testDataNe", it.result.get("price").toString())
                holder.tvpriceBookingTour.setText(mBaseCustom.convertLongtoCurrency(it.result.get("price") as Long?))
            }
        }

        holder.btnRemoveBooking.setOnClickListener { handeleRemoveBooling(bookingTour, position) }
        holder.loItemBookingTour.setOnClickListener {
            handleSeenDetailBookingTour(bookingTour)
        }

    }

    private fun handleSeenDetailBookingTour(bookingTour: ItemIdTour) {
        val intent: Intent = Intent(context, DetailBookingTourActivity::class.java)
        val bundle: Bundle = Bundle()

        bundle.putSerializable("object_booking_tour", bookingTour)

        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    private fun handeleRemoveBooling(bookingTour: ItemIdTour, position: Int) {
        mTourService.removeBookingTourWithId(
            bookingTour.idTour,
            bookingTour.createAt,
            mFirebase.getCurrentUser()?.uid.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                mListBookingTour.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mListBookingTour.size
    }
}

//data class dataBooking(
//    var
//)