package com.example.jombaapp.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.customers.CustomerHome
import com.example.jombaapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()
        registerEvents()
    }

    private var email = ""
    private var user = ""
    private var phone = ""
    private fun registerEvents() {
        binding.btnRegister.setOnClickListener {
            email = binding.etEmail.text.toString().trim()
            user = binding.etName.text.toString().trim()
            phone = binding.etPhoneNumber.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            val verifyPass = binding.etVerifyPassword.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty() && user.isNotEmpty() && phone.isNotEmpty()) {
                if (pass == verifyPass) {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            createUserDetails(timeStamp)
                            binding.etName.text?.clear()
                            binding.etPhoneNumber.text?.clear()
                            binding.etEmail.text?.clear()
                            binding.etPassword.text?.clear()
                            binding.etVerifyPassword.text?.clear()
                            Toast.makeText(
                                this,
                                "Registration successful",
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
                        "Passwords Don't Match",
                        Toast.LENGTH_SHORT
                    ).show()
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

    val timeStamp = System.currentTimeMillis()
    private fun createUserDetails(timeStamp: Long) {
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()

        hashMap["uid"] = "$uid"
        hashMap["name"] = "$user"
        hashMap["email"] = "$email"
        hashMap["phone"] = "$phone"

        val ref = FirebaseDatabase.getInstance().getReference("registeredUser")
        ref.child("$uid")
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Registered Successfully",
                    Toast.LENGTH_SHORT
                ).show()

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Registration Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }
}