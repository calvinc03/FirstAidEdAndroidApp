package com.calvinchen.firstaidlearning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_lookup_item.view.*

class LookupItemAdapter(private val lookupList: List<LookupItem>) :
    RecyclerView.Adapter<LookupItemAdapter.LookupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_lookup_item,
            parent, false)
        return LookupViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        val currentItem = lookupList[position]

        holder.image.setImageDrawable(currentItem.imageResource)
        holder.tvTitle.text = currentItem.title
        holder.tvCategory.text = currentItem.category
    }
    override fun getItemCount() = lookupList.size

    inner class LookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.lookup_image
        val tvTitle: TextView = itemView.lookup_title
        val tvCategory: TextView = itemView.lookup_category

        init {
            itemView.setOnClickListener {
                val openDetails = LookupListFragmentDirections.openDetails(adapterPosition)
                Navigation.findNavController(it).navigate(openDetails)
            }
        }
    }
}