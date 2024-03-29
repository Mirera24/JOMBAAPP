package com.example.jombaapp.customers.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.jombaapp.databinding.ActivityScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class Schedule : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding

    lateinit var tvDate: TextView
    lateinit var btnshowdatepicker: Button

    private val calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            intent
            collectorName.text = intent.getStringExtra("collectorName")
            collectorLocation.text = intent.getStringExtra("collectorLocation")
            payRate.text = intent.getStringExtra("payRate")
        }

        tvDate = binding.tvSelectDate
        btnshowdatepicker = binding.btnshowDatePicker

        btnshowdatepicker.setOnClickListener {
            showDatePicker()
        }

        val btnPickTime = binding.btnPickTime
        val tvTime = binding.tvTime

        btnPickTime.setOnClickListener {
            val cal = java.util.Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.HOUR_OF_DAY, minute)
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
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
}