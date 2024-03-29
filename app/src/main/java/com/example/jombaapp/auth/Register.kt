package com.example.jombaapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jombaapp.R
import com.example.jombaapp.customers.CustomerHome
import com.example.jombaapp.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, CustomerHome::class.java))
            finish()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}