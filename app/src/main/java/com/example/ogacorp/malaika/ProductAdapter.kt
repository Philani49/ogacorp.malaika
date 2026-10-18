package com.example.ogacorp.malaika

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val cardWidthPx: Int? = null) :
    RecyclerView.Adapter<ProductAdapter.VH>() {

    private var items: List<Product> = emptyList()

    fun submit(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvAvailable: TextView = v.findViewById(R.id.tvAvailable)
        val tvPrice: TextView = v.findViewById(R.id.tvPrice)
        val ivClothingImage: ImageView = v.findViewById(R.id.ivClothingImage)
        val tvCategory: TextView = v.findViewById(R.id.tvCategory)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvSizes: TextView = v.findViewById(R.id.tvSizes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_clothing, parent, false)
        cardWidthPx?.let { w -> v.layoutParams = v.layoutParams.apply { width = w } }
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]

        holder.tvName.text = p.name
        holder.tvCategory.text = p.category
        holder.tvPrice.text = "R${p.price.toInt()}"
        holder.tvSizes.text = "Size: ${p.sizes}"

        // --- FORCE IMAGE MAPPING ---
        val imageResId = when (p.name.lowercase().trim()) {
            "casual cotton blouse" -> R.drawable.blouse
            "denim jacket" -> R.drawable.w_denimjacket
            "satin wrap shirt" -> R.drawable.satin_wrapshirt
            "rafferiza floral dress" -> R.drawable.rafferiza_floral
            "manfinity black shirt" -> R.drawable.manfinity_black
            "white shirt" -> R.drawable.men_whiteshirt
            "cargo shorts" -> R.drawable.men_cargoshorts
            "men chinos" -> R.drawable.men_chinos
            "black cartoon tshirt" -> R.drawable.kids_shirt
            "kids denim" -> R.drawable.k_denim
            // Fallback based on category if a name has a typo
            else -> {
                when (p.category.lowercase()) {
                    "women" -> R.drawable.blouse
                    "men" -> R.drawable.men_whiteshirt
                    "kids" -> R.drawable.kids_shirt
                    else -> R.drawable.ic_launcher_background
                }
            }
        }

        holder.ivClothingImage.setImageResource(imageResId)

        // Handle Availability Badge
        if (p.available) {
            holder.tvAvailable.text = "Available"
            holder.tvAvailable.setBackgroundResource(R.drawable.badge_bg_available)
            holder.tvAvailable.setTextColor(Color.WHITE)
        } else {
            holder.tvAvailable.text = "Sold out"
            holder.tvAvailable.setBackgroundResource(R.drawable.badge_bg_available)
            holder.tvAvailable.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount() = items.size
}