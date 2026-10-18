package com.example.ogacorp.malaika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.VH>() {

    private var items: List<BeautyService> = emptyList()

    fun submit(list: List<BeautyService>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: ShapeableImageView = v.findViewById(R.id.ivServiceIcon)
        val name: TextView = v.findViewById(R.id.tvServiceName)
        val price: TextView = v.findViewById(R.id.tvServicePrice)
        val desc: TextView = v.findViewById(R.id.tvServiceDesc)
        val duration: TextView = v.findViewById(R.id.tvServiceDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position]

        // Set Text Data
        holder.name.text = s.name
        holder.price.text = "R${s.price.toInt()}"
        holder.desc.text = s.description
        holder.duration.text = "${s.durationMinutes} min"

        // Set Images based on Category
        val imageResId = when {
            s.category.contains("Nail", ignoreCase = true) -> R.drawable.gel
            s.category.contains("Lash", ignoreCase = true) -> R.drawable.eyelashes
            s.category.contains("Massage", ignoreCase = true) -> R.drawable.bodymassage
            else -> R.drawable.gel // Default fallback
        }

        holder.icon.setImageResource(imageResId)
    }

    override fun getItemCount() = items.size
}