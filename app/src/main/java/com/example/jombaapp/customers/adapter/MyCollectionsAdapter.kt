package com.example.jombaapp.customers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jombaapp.customers.model.MyCollectionsData
import com.example.jombaapp.databinding.CollectionItemBinding

class MyCollectionsAdapter(private val list: MutableList<MyCollectionsData>) :
    RecyclerView.Adapter<MyCollectionsAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(private val collectionItemBinding: CollectionItemBinding) :
        RecyclerView.ViewHolder(collectionItemBinding.root) {
        fun setData(collection: MyCollectionsData) {
            collectionItemBinding.apply {
                collectorName.text = collection.collectorName
                collectorLocation.text = collection.collectorLocation
                collectionTime.text = collection.collectionTime
                collectionDate.text = collection.collectionDate
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val collectionItemBinding =
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(collectionItemBinding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val collection = list[position]
        holder.setData(collection)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}