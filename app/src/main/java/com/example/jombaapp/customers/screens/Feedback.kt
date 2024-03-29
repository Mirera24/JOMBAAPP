package com.example.jombaapp.customers.screens

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.customers.CustomerHome
import com.example.jombaapp.customers.model.TestimonialData
import com.example.jombaapp.databinding.ActivityFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Feedback : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnSubmit.setOnClickListener {
            validateData()
        }
    }

    private var testimonialName = ""
    private var testimonialMessage = ""
    private fun validateData() {
        testimonialName = binding.testimonialName.text.toString().trim()
        testimonialMessage = binding.testimonialMessage.text.toString().trim()
        if (testimonialName.isEmpty()) {
            Toast.makeText(this, "Enter Collector Name", Toast.LENGTH_SHORT).show()
        } else if (testimonialMessage.isEmpty()) {
            Toast.makeText(this, "Enter Collector Location", Toast.LENGTH_SHORT).show()
        } else {
            saveToDatabase(testimonialName, testimonialMessage)
            binding.testimonialName.text?.clear()
            binding.testimonialMessage.text?.clear()
        }

    }

    private fun saveToDatabase(testimonialName: String, testimonialMessage: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading data")

        val testimonialData = TestimonialData(testimonialName, testimonialMessage)
        val testimonialMap = HashMap<String, Any>()
        testimonialMap["testimonialName"] = testimonialName
        testimonialMap["testimonialMessage"] = testimonialMessage

        val database = FirebaseDatabase.getInstance().reference.child("Testimonials")
        val testimonialKey = database.push().key
        if (testimonialKey != null) {
            progressDialog.show()
            database.child(testimonialKey).setValue(testimonialData)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Testimonial submitted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed to submit testimonial due to ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }



}