package com.example.ogacorp.malaika

data class BeautyService(
    val name: String = "",
    val description: String = "",
    val category: String = "",      // Nails, Eyelashes or Massage
    val price: Double = 0.0,
    val durationMinutes: Int = 0,
    val order: Int = 0
)
