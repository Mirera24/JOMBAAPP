package com.example.jombaapp.customers.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.R
import com.example.jombaapp.databinding.ActivityGpsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GPS : AppCompatActivity() {
    private lateinit var binding: ActivityGpsBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGpsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            mMap = googleMap
            mMap.uiSettings.isZoomControlsEnabled = true
            // Call function to add markers around Meru
            addGarbageCollectionAreas()
        }
    }

    private fun addGarbageCollectionAreas() {
        // Coordinates of Meru
        val meru = LatLng(0.0646, 37.6375)

        // Add marker for Meru AIzaSyBysSedUGsoKsG0NrqvFGwshrFzAmdCLWM
        mMap.addMarker(MarkerOptions().position(meru).title("Meru"))

        // Add garbage collection areas around Meru (Dummy markers for demonstration)
        val collectionAreas = listOf(
            LatLng(0.065, 37.636), // Example collection area 1
            LatLng(0.063, 37.638), // Example collection area 2
            LatLng(0.065, 37.639)  // Example collection area 3
            // Add more coordinates as needed to represent different collection areas
        )

        // Add markers for garbage collection areas
        collectionAreas.forEachIndexed { index, latLng ->
            mMap.addMarker(
                MarkerOptions().position(latLng).title("Garbage Collection Area ${index + 1}")
            )
        }

        // Move camera to Meru's location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meru, 12f))
    }
}
