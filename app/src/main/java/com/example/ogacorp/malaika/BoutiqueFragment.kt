package com.example.ogacorp.malaika

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup

class BoutiqueFragment : Fragment() {

    private lateinit var adapter: ProductAdapter
    private var allProducts: List<Product> = emptyList()
    private var selectedCategory: String = "All"
    private var tvEmpty: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_boutique, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvEmpty = view.findViewById(R.id.tvEmpty)

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val padding = (32 * displayMetrics.density).toInt()
        val cardWidth = (screenWidth - padding) / 2

        adapter = ProductAdapter(cardWidthPx = cardWidth)

        view.findViewById<RecyclerView>(R.id.rvProducts).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@BoutiqueFragment.adapter
        }

        view.findViewById<ChipGroup>(R.id.chipGroup).setOnCheckedStateChangeListener { _, ids ->
            selectedCategory = when (ids.firstOrNull()) {
                R.id.chipWomen -> "Women"
                R.id.chipMen -> "Men"
                R.id.chipKids -> "Kids"
                else -> "All"
            }
            filterList()
        }

        // --- HARDCODED PRODUCTS (Matches your Firebase Console perfectly) ---
        allProducts = listOf(
            Product("Casual Cotton Blouse", "Women", 450.0, "XS S M L XL", true, true, "👚"),
            Product("Denim Jacket", "Women", 650.0, "S M L", true, false, "🧥"),
            Product("Satin Wrap Shirt", "Women", 380.0, "S M L XL", true, false, "👚"),
            Product("Rafferiza Floral Dress", "Women", 350.0, "XS S M L XL", true, true, "👗"),
            Product("Manfinity Black Shirt", "Men", 400.0, "S M L", true, true, "👔"),
            Product("White Shirt", "Men", 350.0, "S M L XL", true, false, "👔"),
            Product("Cargo Shorts", "Men", 340.0, "S M L XL", true, true, "🩳"),
            Product("Men Chinos", "Men", 450.0, "30 32 34 36", false, false, "👖"),
            Product("Black Cartoon Tshirt", "Kids", 150.0, "7-8 9-10 11-12 13-14", true, true, "👕"),
            Product("Kids Denim", "Kids", 250.0, "4-5 6-7 8-9", true, false, "👖")
        )

        filterList() // Show them instantly
    }

    private fun filterList() {
        val filtered = if (selectedCategory == "All") {
            allProducts
        } else {
            allProducts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }

        adapter.submit(filtered)

        if (filtered.isEmpty()) {
            tvEmpty?.visibility = View.VISIBLE
            tvEmpty?.text = "No items found for $selectedCategory"
        } else {
            tvEmpty?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        tvEmpty = null
        super.onDestroyView()
    }
}