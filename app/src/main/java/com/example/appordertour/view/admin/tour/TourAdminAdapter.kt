package com.example.appordertour.view.admin.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appordertour.R
import com.example.appordertour.model.Tour
import com.example.appordertour.service.TourService
import com.example.appordertour.util.BaseCustom
import com.squareup.picasso.Picasso

class TourAdminAdapter(private val mListTour: MutableList<Tour>) :
    RecyclerView.Adapter<TourAdminAdapter.TourAdminViewHolder>() {

    private lateinit var context: android.content.Context
    private val mBaseCustom = BaseCustom()
    private val mTourService = TourService()

    class TourAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_admin_tour: ImageView = itemView.findViewById(R.id.img_admin_tour)
        val tv_name_admin_tour: TextView = itemView.findViewById(R.id.tv_name_admin_tour)
        val tv_descripton_admin_tour: TextView =
            itemView.findViewById(R.id.tv_descripton_admin_tour)
        val tv_price_admin_tour: TextView = itemView.findViewById(R.id.tv_price_admin_tour)
        val btn_popup_menu_admin_tour: ImageButton =
            itemView.findViewById(R.id.btn_popup_menu_admin_tour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourAdminViewHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tour_admin, parent, false)
        return TourAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourAdminViewHolder, position: Int) {
        val tour: Tour = mListTour[position]

        Picasso.get().load(tour.avater).into(holder.img_admin_tour)
        holder.tv_name_admin_tour.setText(tour.nameTour)
        holder.tv_descripton_admin_tour.setText(tour.description)
        holder.tv_price_admin_tour.setText(mBaseCustom.convertLongtoCurrency(tour.price))
        holder.btn_popup_menu_admin_tour.setOnClickListener {
            showPopupMenu(it, position, tour.id)
        }

    }

    private fun showPopupMenu(view: View, position: Int, idTour: String?) {
        val popupMenus = PopupMenu(context, view)
        popupMenus.inflate(R.menu.show_menu_handle)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editBtn -> {
                    Toast.makeText(context, "edit text", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.deleteBtn -> {
                    handleRemoveItem(position, idTour)
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(menu, true)

    }

    private fun handleRemoveItem(position: Int, idTour: String?) {
        mTourService.deleteTourWithId(idTour.toString()) {
            if (it) {
                mListTour.removeAt(position)
                notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Không thể xóa tour này", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return mListTour.size
    }
}