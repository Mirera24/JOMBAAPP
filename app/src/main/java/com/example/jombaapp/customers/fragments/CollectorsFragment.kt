package com.example.jombaapp.customers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.R
import com.example.jombaapp.customers.adapter.CollectorsAdapter
import com.example.jombaapp.customers.adapter.TestimonialAdapter
import com.example.jombaapp.customers.model.CollectorData
import com.example.jombaapp.customers.model.TestimonialData
import com.example.jombaapp.customers.screens.CollectorInformation
import com.example.jombaapp.databinding.FragmentCollectorsBinding


class CollectorsFragment : Fragment(), CollectorsAdapter.OnCollectorClickListener {
    private lateinit var binding: FragmentCollectorsBinding

    private lateinit var adapter: CollectorsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var collectorArrayList: MutableList<CollectorData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataInitialize()
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

    private fun dataInitialize() {
        collectorArrayList = arrayListOf(
            CollectorData("", "Mathare Collectoors", "Mathare", "", "", ""),
            CollectorData("", "Boma Collectoors", "Boma", "", "", ""),
            CollectorData("", "kasarani Collectoors", "kasarani", "", "", ""),
            CollectorData("", "Ruiru Collectoors", "Ruiru", "", "", ""),
            CollectorData("", "SouthB Collectoors", "SouthB", "", "", ""),
            CollectorData("", "Kino Collectoors", "Kino", "", "", ""),
            CollectorData("", "Mathare Collectoors", "Mathare", "", "", ""),
            CollectorData("", "Mathare Collectoors", "Mathare", "", "", "")
        )
    }
}