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
import com.example.jombaapp.customers.adapter.MyCollectionsAdapter
import com.example.jombaapp.customers.adapter.TestimonialAdapter
import com.example.jombaapp.customers.model.MyCollectionsData
import com.example.jombaapp.customers.model.TestimonialData
import com.example.jombaapp.customers.screens.Feedback
import com.example.jombaapp.customers.screens.GPS
import com.example.jombaapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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

        binding.btnTestify.setOnClickListener {
            val intent = Intent(requireActivity(), Feedback::class.java)
            startActivity(intent)
        }

        binding.maps.setOnClickListener {
            val intent = Intent(requireActivity(), GPS::class.java)
            startActivity(intent)
        }

        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.pickUpRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyCollectionsAdapter(myCollectionArrayList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        dataInitialize2()
        val layoutManager2 = LinearLayoutManager(context)
        recyclerView2 = view.findViewById(R.id.testimonials)
        recyclerView2.layoutManager = layoutManager2
        recyclerView2.setHasFixedSize(true)
        adapter2 = TestimonialAdapter(testimonialArrayList)
        recyclerView2.adapter = adapter2
        adapter2.notifyDataSetChanged()
    }

    private fun dataInitialize() {
        myCollectionArrayList = arrayListOf(
            MyCollectionsData("", "Mathare Collectors", "Mathare", "12:00", "12/03/2024"),
            MyCollectionsData("", "Kasarani Collectors", "Kasarani", "12:00", "12/03/2024"),
            MyCollectionsData("", "Boma Collectors", "Boma", "12:00", "12/03/2024"),
            MyCollectionsData("", "Ruiru Collectors", "Ruiru", "12:00", "12/03/2024"),
            MyCollectionsData("", "Mathare Collectors", "Kasarani", "12:00", "12/03/2024"),
            MyCollectionsData("", "Kasarani Collectors", "Kasarani", "12:00", "12/03/2024"),
            MyCollectionsData("", "Boma Collectors", "Kasarani", "12:00", "12/03/2024"),
            MyCollectionsData("", "Ruiru Collectors", "Kasarani", "12:00", "12/03/2024")
        )
    }


    private fun dataInitialize2() {
        testimonialArrayList = arrayListOf(
            TestimonialData("Rayooo", "Good"),
            TestimonialData("Moris", "Good"),
            TestimonialData("Booker", "Good"),
            TestimonialData("Buzeki", "Good"),
            TestimonialData("Rayooo", "Good"),
            TestimonialData("Moris", "Good"),
            TestimonialData("Booker", "Good"),
            TestimonialData("Buzeki", "Good")
        )
    }


}