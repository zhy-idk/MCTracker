package com.example.mctracker

import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Long.valueOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_select)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calendarView : CalendarView = findViewById(R.id.calendarView)
        val buttonSelect : Button = findViewById(R.id.buttonSelect)

        val dateMillis = Date(calendarView.date)
        var date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateMillis)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        }

        buttonSelect.setOnClickListener {
            val intent = intent
            intent.putExtra("date", date)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}