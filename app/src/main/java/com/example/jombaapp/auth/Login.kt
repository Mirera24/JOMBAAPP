package com.example.jombaapp.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.customers.CustomerHome
import com.example.jombaapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            registerEvents()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }
    }

    private fun registerEvents() {
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.etEmail.text?.clear()
                        binding.etPassword.text?.clear()
                        Toast.makeText(
                            this,
                            "Logged In successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, CustomerHome::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            } else {
                Toast.makeText(
                    this,
                    "Empty Fields Not Allowed",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }
}