package com.example.jombaapp.customers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.customers.model.TestimonialData
import com.example.jombaapp.databinding.TestimonialBinding

class TestimonialAdapter(private val list: MutableList<TestimonialData>) :
    RecyclerView.Adapter<TestimonialAdapter.TestimonialViewHolder>() {

    inner class TestimonialViewHolder(val testimonialBinding: TestimonialBinding) :
        RecyclerView.ViewHolder(testimonialBinding.root) {
        fun setData(testimonialData: TestimonialData) {
            testimonialData.apply {
                testimonialBinding.testimonialName.text = testimonialData.testimonialName
                testimonialBinding.testimonialMessage.text = testimonialData.testimonialMessage
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialViewHolder {
        val testimonialBinding =
            TestimonialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestimonialViewHolder(testimonialBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TestimonialViewHolder, position: Int) {
        val testimonial = list[position]
        holder.setData(testimonial)
    }
}