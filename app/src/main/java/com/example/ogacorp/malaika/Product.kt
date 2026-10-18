package com.example.ogacorp.malaika

data class Product(
    val name: String = "",
    val category: String = "",      // Women, Men or Kids
    val price: Double = 0.0,
    val sizes: String = "",
    val available: Boolean = true,
    val newArrival: Boolean = false,
    val emoji: String = "👕"
)
