package com.example.jombaapp.customers.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.databinding.ActivityScheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Schedule : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private lateinit var tvDate: TextView
    private lateinit var btnShowDatePicker: Button
    private lateinit var btnSchedule: Button
    private lateinit var tvTime: TextView
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val collectorName = binding.collectorName
        val collectorLocation = binding.collectorLocation
        val payRate = binding.payRate
        tvDate = binding.tvSelectDate
        btnShowDatePicker = binding.btnshowDatePicker
        tvTime = binding.tvTime
        btnSchedule = binding.btnSchedule

        btnShowDatePicker.setOnClickListener {
            showDatePicker()
        }

        btnSchedule.setOnClickListener {
            val selectedDate = tvDate.text.toString()
            val selectedTime = tvTime.text.toString()
            val collectorName = collectorName.text.toString()
            val collectorLocation = collectorLocation.text.toString()
            val payRate = payRate.text.toString()

            saveScheduleToFirebase(selectedDate, selectedTime, collectorName, collectorLocation, payRate)
        }

        val cal = Calendar.getInstance()
        binding.btnPickTime.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tvTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate: String = dateFormat.format(selectedDate.time)
                tvDate.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun saveScheduleToFirebase(date: String, time: String, collectorName: String, collectorLocation: String, payRate: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Schedules")
        val scheduleData = mapOf(
            "uid" to uid,
            "date" to date,
            "time" to time,
            "collectorName" to collectorName,
            "collectorLocation" to collectorLocation,
            "payRate" to payRate
        )

        // Push the data to Firebase
        databaseRef.push().setValue(scheduleData)
            .addOnSuccessListener {
                Toast.makeText(this,"Uploaded Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Upload Failed", Toast.LENGTH_SHORT).show()
            }
    }
}
