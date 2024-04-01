package com.example.jombaapp.customers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.R
import com.example.jombaapp.customers.adapter.CollectorsAdapter
import com.example.jombaapp.customers.model.CollectorData
import com.example.jombaapp.customers.screens.AddCollector
import com.example.jombaapp.customers.screens.CollectorInformation
import com.example.jombaapp.databinding.FragmentCollectorsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class CollectorsFragment : Fragment(), CollectorsAdapter.OnCollectorClickListener {
    private lateinit var binding: FragmentCollectorsBinding

    private lateinit var adapter: CollectorsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var collectorArrayList: MutableList<CollectorData>

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
            val intent = Intent(requireActivity(), AddCollector::class.java)
            startActivity(intent)
        }

        // Initialize collectorArrayList
        collectorArrayList = mutableListOf()

        getCollectors()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.collectorsRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CollectorsAdapter(collectorArrayList, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onCollectorClick(collector: CollectorData, position: Int) {
        val intent = Intent(requireActivity(), CollectorInformation::class.java)
        intent.putExtra("collectorUrl", collector.collectorUrl.toString())
        intent.putExtra("collectorName", collector.collectorName)
        intent.putExtra("collectorLocation", collector.collectorLocation)
        intent.putExtra("payRate", collector.payRate)
        intent.putExtra("about", collector.about)
        startActivity(intent)
    }

    private fun getCollectors() {
        database = Firebase.database.reference
        database.child("Collectors").get()
            .addOnSuccessListener { dataSnapshot ->
                for (collectorSnapshot in dataSnapshot.children) {
                    val collectorUrl =
                        collectorSnapshot.child("collectorUrl").getValue(String::class.java)
                    val collectorName =
                        collectorSnapshot.child("collectorName").getValue(String::class.java)
                    val collectorLocation =
                        collectorSnapshot.child("collectorLocation").getValue(String::class.java)
                    val payRate = collectorSnapshot.child("payRate").getValue(String::class.java)
                    val about = collectorSnapshot.child("about").getValue(String::class.java)
                    val uid = collectorSnapshot.child("uid").getValue(String::class.java)

                    if (collectorUrl != null && collectorName != null && collectorLocation != null && payRate != null && about != null && uid != null) {
                        val collector = CollectorData(
                            uid, collectorName, collectorLocation, payRate, about,collectorUrl
                        )
                        collectorArrayList.add(collector)
                    }
                }
                adapter.notifyDataSetChanged()

            }
    }
}