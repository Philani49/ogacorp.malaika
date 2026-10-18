package com.example.ogacorp.malaika

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val serviceAdapter = ServiceAdapter()
        val cardWidth = (150 * resources.displayMetrics.density).toInt()
        val arrivalAdapter = ProductAdapter(cardWidthPx = cardWidth)

        view.findViewById<RecyclerView>(R.id.rvServices).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceAdapter
            isNestedScrollingEnabled = false
        }
        view.findViewById<RecyclerView>(R.id.rvArrivals).apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = arrivalAdapter
        }

        fun goTo(tabId: Int) = (activity as? HomeActivity)?.selectTab(tabId)
        view.findViewById<View>(R.id.btnBookService).setOnClickListener { goTo(R.id.nav_book) }
        view.findViewById<View>(R.id.btnViewServices).setOnClickListener { goTo(R.id.nav_book) }
        view.findViewById<View>(R.id.btnShopBoutique).setOnClickListener { goTo(R.id.nav_boutique) }
        view.findViewById<View>(R.id.btnViewBoutique).setOnClickListener { goTo(R.id.nav_boutique) }

        // --- HARDCODED SERVICES FOR VIDEO ---
        val dummyServices = listOf(
            BeautyService("Gel Nails", "Long-lasting gel manicure", "Nails", 350.0, 60, 1),
            BeautyService("Classic Eyelashes", "Classic lash extensions", "Eyelashes", 250.0, 45, 2),
            BeautyService("Full Body Massage", "Swedish relaxation massage", "Massage", 750.0, 90, 3)
        )
        serviceAdapter.submit(dummyServices)

        // --- HARDCODED NEW ARRIVALS FOR VIDEO ---
        val dummyArrivals = listOf(
            Product("Casual Cotton Blouse", "Women", 450.0, "XS S M L XL", true, true, "👚"),
            Product("Rafferiza Floral Dress", "Women", 350.0, "XS S M L XL", true, true, "👗"),
            Product("Manfinity Black Shirt", "Men", 400.0, "S M L", true, true, "👔")
        )
        arrivalAdapter.submit(dummyArrivals)
    }
}