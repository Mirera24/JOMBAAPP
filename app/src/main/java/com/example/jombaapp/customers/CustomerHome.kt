package com.example.jombaapp.customers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.jombaapp.R
import com.example.jombaapp.customers.fragments.CollectorsFragment
import com.example.jombaapp.customers.fragments.HomeFragment
import com.example.jombaapp.customers.fragments.ProfileFragment
import com.example.jombaapp.databinding.ActivityCustomerHomeBinding

class CustomerHome : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomerHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.collectors -> replaceFragment(CollectorsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {
                }
            }
            true
        }
        if (resources.getColor(R.color.background_tint_dark) == resources.getColor(R.color.background_tint_dark)) {
            binding.bottomNavigationView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.background_tint_dark
                )
            )
        } else {
            binding.bottomNavigationView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.background_tint_light
                )
            )


        }


    }

    private fun replaceFragment(
        fragment:
        Fragment
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}