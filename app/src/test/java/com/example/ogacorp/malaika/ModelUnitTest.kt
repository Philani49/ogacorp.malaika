package com.example.ogacorp.malaika
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class ModelUnitTest {
    @Test
    fun productStoresBoutiqueDetails() {
        val product = Product(
            name = "Denim Jacket",
            category = "Women",
            price = 650.0,
            sizes = "S M L",
            available = true,
            newArrival = false,
            emoji = "🧥"
        )

        assertEquals("Denim Jacket", product.name)
        assertEquals("Women", product.category)
        assertEquals(650.0, product.price, 0.0)
        assertTrue(product.available)
    }
}