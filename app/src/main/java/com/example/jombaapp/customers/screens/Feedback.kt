package com.example.jombaapp.customers.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jombaapp.R
import com.example.jombaapp.databinding.ActivityFeedbackBinding

class Feedback : AppCompatActivity() {
    private lateinit var binding : ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
    }
}