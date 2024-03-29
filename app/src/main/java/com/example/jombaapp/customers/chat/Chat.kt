package com.example.jombaapp.customers.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jombaapp.R
import com.example.jombaapp.databinding.ActivityChatBinding

class Chat : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}