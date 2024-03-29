package com.example.jombaapp.customers.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.jombaapp.customers.chat.Chat
import com.example.jombaapp.databinding.ActivityCollectorInfomationBinding

class CollectorInformation : AppCompatActivity() {
    private lateinit var binding: ActivityCollectorInfomationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCollectorInfomationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this)
            .load(intent.getStringExtra("collectorUrl"))
            .into(binding.collectorUrl)
        binding.apply {
            intent
            collectorName.text = intent.getStringExtra("collectorName")
            collectorLocation.text = intent.getStringExtra("collectorLocation")
            payRate.text = intent.getStringExtra("payRate")
            about.text = intent.getStringExtra("about")
        }

        binding.btnSchedule.setOnClickListener {
            val intent = Intent(this, Schedule::class.java)
            intent.putExtra("collectorName", binding.collectorName.text.toString())
            intent.putExtra("collectorLocation", binding.collectorLocation.text.toString())
            intent.putExtra("payRate", binding.payRate.text.toString())
            startActivity(intent)
        }

        binding.btnChat.setOnClickListener {
            startActivity(Intent(this, Chat::class.java))
        }


    }
}