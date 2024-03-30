package com.example.jombaapp.customers.screens

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.databinding.ActivityAddCollectorBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddCollector : AppCompatActivity() {
    private lateinit var binding: ActivityAddCollectorBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var jobUri: Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        jobUri = it
        binding.selectImage.setImageURI(jobUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCollectorBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.selectImage.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.btnAddCollector.setOnClickListener {
            validateData()
        }
    }

    private var collectorName = ""
    private var collectorLocation = ""
    private var payRate = ""
    private var about = ""

    private fun validateData() {
        Log.d(TAG, "Validating Data")
        collectorName = binding.collectorName.text.toString().trim()
        collectorLocation = binding.collectorLocation.text.toString().trim()
        payRate = binding.payRate.text.toString().trim()
        about = binding.about.text.toString().trim()

        if (collectorName.isEmpty()) {
            Toast.makeText(this, "Enter Collector Name", Toast.LENGTH_SHORT).show()
        } else if (collectorLocation.isEmpty()) {
            Toast.makeText(this, "Enter Collector Location", Toast.LENGTH_SHORT).show()
        } else if (payRate.isEmpty()) {
            Toast.makeText(this, "Enter Pay Rate", Toast.LENGTH_SHORT).show()
        } else if (about.isEmpty()) {
            Toast.makeText(this, "Enter About", Toast.LENGTH_SHORT).show()
        } else {
            uploadJobToStorage()
            binding.collectorName.text?.clear()
            binding.collectorLocation.text?.clear()
            binding.payRate.text?.clear()
            binding.about.text?.clear()
        }

    }

    private fun uploadJobToStorage() {
        Log.d(TAG, "Uploading")
        progressDialog.setMessage("Uploading Job")
        progressDialog.show()

        val timeStamp = System.currentTimeMillis()
        val filePathAndName = "collector/$timeStamp"
        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)
        storageReference.putFile(jobUri!!)
            .addOnSuccessListener { taskSnapShot ->
                val uriask: Task<Uri> = taskSnapShot.storage.downloadUrl
                while (!uriask.isSuccessful);
                val uploadedImageUrl = "${uriask.result}"

                uploadJobInfoToDb(uploadedImageUrl, timeStamp)

                progressDialog.dismiss()
            }
    }

    private fun uploadJobInfoToDb(uploadedImageUrl: String, timeStamp: Long) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading data")
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["id"] = "$timeStamp"
        hashMap["uid"] = "$uid"
        hashMap["collectorName"] = "$collectorName"
        hashMap["collectorLocation"] = "$collectorLocation"
        hashMap["payRate"] = "$payRate"
        hashMap["about"] = "$about"
        hashMap["collectorUrl"] = "$uploadedImageUrl"

        val ref = FirebaseDatabase.getInstance().getReference("Collectors")
        ref.child("$timeStamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Uploaded",
                    Toast.LENGTH_SHORT
                ).show()
                jobUri = null
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Uploading Job Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

}