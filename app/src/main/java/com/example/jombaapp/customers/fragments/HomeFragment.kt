package com.example.jombaapp.customers.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.R
import com.example.jombaapp.customers.adapter.MyCollectionsAdapter
import com.example.jombaapp.customers.adapter.TestimonialAdapter
import com.example.jombaapp.customers.model.MyCollectionsData
import com.example.jombaapp.customers.model.TestimonialData
import com.example.jombaapp.customers.model.UserData
import com.example.jombaapp.customers.screens.Feedback
import com.example.jombaapp.customers.screens.GPS
import com.example.jombaapp.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var adapter: MyCollectionsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var myCollectionArrayList: MutableList<MyCollectionsData>

    private lateinit var adapter2: TestimonialAdapter
    private lateinit var recyclerView2: RecyclerView
    private lateinit var testimonialArrayList: MutableList<TestimonialData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Initialize myCollectionArrayList
        myCollectionArrayList = mutableListOf()

        // Fetch and display user name
        fetchAndDisplayUserName()

        binding.btnTestify.setOnClickListener {
            val intent = Intent(requireActivity(), Feedback::class.java)
            startActivity(intent)
        }

        binding.maps.setOnClickListener {
            val intent = Intent(requireActivity(), GPS::class.java)
            startActivity(intent)
        }

        getSchedules()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.pickUpRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyCollectionsAdapter(myCollectionArrayList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        getTestimonials()
        val layoutManager2 = LinearLayoutManager(context)
        recyclerView2 = view.findViewById(R.id.testimonials)
        recyclerView2.layoutManager = layoutManager2
        recyclerView2.setHasFixedSize(true)
        adapter2 = TestimonialAdapter(testimonialArrayList)
        recyclerView2.adapter = adapter2
        adapter2.notifyDataSetChanged()
    }

    private fun getSchedules() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if (uid != null) {
            val database = Firebase.database.reference.child("Schedules")
            database.orderByChild("uid").equalTo(uid).get().addOnSuccessListener { dataSnapshot ->
                myCollectionArrayList = mutableListOf() // Initialize the list
                for (trackSnapshot in dataSnapshot.children) {
                    val collectorName = trackSnapshot.child("collectorName").getValue(String::class.java)
                    val collectorLocation = trackSnapshot.child("collectorLocation").getValue(String::class.java)
                    val collectionTime = trackSnapshot.child("collectionTime").getValue(String::class.java)
                    val collectionDate = trackSnapshot.child("collectionDate").getValue(String::class.java)

                    if (collectorName != null && collectorLocation != null && collectionTime != null && collectionDate != null) {
                        val schedule = MyCollectionsData(uid, collectorName, collectorLocation, collectionTime, collectionDate)
                        myCollectionArrayList.add(schedule)
                    }
                }
                adapter.notifyDataSetChanged()
                Log.d("data", myCollectionArrayList.toString())
            }.addOnFailureListener { e ->
                // Handle failure
                Log.e(TAG, "Failed to retrieve schedules: ${e.message}")
            }
        } else {
            Log.e(TAG, "Current user UID is null")
        }
    }


    private fun fetchAndDisplayUserName() {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val userRef =
                FirebaseDatabase.getInstance().getReference("registeredUser").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("Tag", "exists")

                        val userData = snapshot.getValue(UserData::class.java)
                        Log.e("UserData", userData.toString())
                        if (userData != null) {
                            val userName = userData.name ?: "Unknown"
                            Log.e("HomeFragment", "User name retrieved: $userName")
                            binding.userName.text = userName
                        } else {
                            Log.e(TAG, "Userdata Doesn't Exist")
                        }
                    } else {
                        Log.e(TAG, "User data snapshot does not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    Log.e("HomeFragment", "Failed to fetch user data: ${error.message}")
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Log.e("HomeFragment", "Current user ID is null")
        }
    }


    private fun getTestimonials() {
        val database = FirebaseDatabase.getInstance().reference.child("Testimonials")
        testimonialArrayList = mutableListOf()

        database.get().addOnSuccessListener { dataSnapshot ->
            for (testimonialSnapshot in dataSnapshot.children) {
                val testimonialName =
                    testimonialSnapshot.child("testimonialName").getValue(String::class.java)
                val testimonialMessage =
                    testimonialSnapshot.child("testimonialMessage").getValue(String::class.java)

                if (testimonialName != null && testimonialMessage != null) {
                    val testimonialData = TestimonialData(testimonialName, testimonialMessage)
                    testimonialArrayList.add(testimonialData)
                }
            }

            // Update adapter with fetched data
            adapter2 = TestimonialAdapter(testimonialArrayList)
            recyclerView2.adapter = adapter2
            adapter2.notifyDataSetChanged()

        }.addOnFailureListener { e ->
            // Handle failure
            Log.e(TAG, "Failed to retrieve testimonials: ${e.message}")
        }
    }


}