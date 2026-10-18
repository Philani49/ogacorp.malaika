package com.example.ogacorp.malaika

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> show(HomeFragment())
                R.id.nav_book -> show(BookingFragment())
                R.id.nav_boutique -> show(BoutiqueFragment())
                R.id.nav_wishlist -> show(WishlistFragment())
                R.id.nav_profile -> show(ProfileFragment())
                else -> return@setOnItemSelectedListener false
            }
            true
        }

        if (savedInstanceState == null) bottomNav.selectedItemId = R.id.nav_home
    }

    fun selectTab(itemId: Int) {
        bottomNav.selectedItemId = itemId
    }

    private fun show(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
