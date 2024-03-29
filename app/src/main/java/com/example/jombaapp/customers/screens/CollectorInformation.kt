package com.example.jombaapp.customers.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jombaapp.R
import com.example.jombaapp.databinding.ActivityCollectorInfomationBinding

class CollectorInformation : AppCompatActivity() {
    private lateinit var binding : ActivityCollectorInfomationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCollectorInfomationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}