package com.example.jombaapp.collector

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.customers.adapter.MyCollectionsAdapter
import com.example.jombaapp.customers.chat.Chat
import com.example.jombaapp.customers.model.MyCollectionsData
import com.example.jombaapp.databinding.ActivityCollecorHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CollecorHome : AppCompatActivity() {
    private lateinit var binding: ActivityCollecorHomeBinding
    private var totalCollections: Int = 0

    private lateinit var auth: FirebaseAuth

    private lateinit var adapter: MyCollectionsAdapter
    private lateinit var recyclerView: RecyclerView
    private var myCollectionArrayList = mutableListOf<MyCollectionsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollecorHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.chat.setOnClickListener {
            startActivity(Intent(this, Chat::class.java))
        }

        getSchedules()

        val layoutManager = LinearLayoutManager(this)
        recyclerView = binding.pickUpRecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyCollectionsAdapter(myCollectionArrayList)
        recyclerView.adapter = adapter

        // Reference to the "Schedules" node in Firebase
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Schedules")

        // Add a ValueEventListener to count the number of schedules
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalCollections = snapshot.childrenCount.toInt()
                displayCollections()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CollecorHome, "Failed to retrieve data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getSchedules() {
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        if (uid != null) {
            val database = FirebaseDatabase.getInstance().reference.child("Schedules")
            database.get().addOnSuccessListener { dataSnapshot ->
                myCollectionArrayList.clear() // Clear the list before adding new data
                for (trackSnapshot in dataSnapshot.children) {
                    val collectorName =
                        trackSnapshot.child("collectorName").getValue(String::class.java)
                    val collectorLocation =
                        trackSnapshot.child("collectorLocation").getValue(String::class.java)
                    val collectionTime = trackSnapshot.child("time").getValue(String::class.java)
                    val collectionDate = trackSnapshot.child("date").getValue(String::class.java)

                    if (collectorName != null && collectorLocation != null && collectionTime != null && collectionDate != null) {
                        val schedule = MyCollectionsData(
                            uid,
                            collectorName,
                            collectorLocation,
                            collectionTime,
                            collectionDate
                        )
                        myCollectionArrayList.add(schedule)
                    }
                }
                adapter.notifyDataSetChanged()
            }.addOnFailureListener { e ->
                // Handle failure
                Log.e(ContentValues.TAG, "Failed to retrieve schedules: ${e.message}")
            }
        } else {
            Log.e(ContentValues.TAG, "Current user UID is null")
        }
    }

    private fun displayCollections() {
        // Calculate the total collection amount
        val totalAmount = totalCollections * 5000

        // Display the total collection amount on the UI
        binding.collections.text = "Ksh $totalAmount"
    }
}
