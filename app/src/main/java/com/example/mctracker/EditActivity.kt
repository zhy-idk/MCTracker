package com.example.mctracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditActivity : AppCompatActivity() {
    lateinit var etDate: EditText
    lateinit var etOdo: EditText
    lateinit var etLiters: EditText
    lateinit var etPrice: EditText
    lateinit var buttonRecord: Button
    lateinit var tvPosition: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etDate = findViewById(R.id.etDate)
        etOdo = findViewById(R.id.etOdo)
        etLiters = findViewById(R.id.etLiters)
        etPrice = findViewById(R.id.etPrice)
        buttonRecord = findViewById(R.id.buttonRecord)
        tvPosition = findViewById(R.id.tvPosition)

        val position = intent.getIntExtra("position", 0)

        tvPosition.text = "Record #${position + 1}"

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()

        val testing =
            sharedPreferences.getStringSet(position.toString(), setOf("0,none,none,none"))
        testing?.forEach { item ->
            val test = item.split(",")
            etDate.setText(test[0])
            etOdo.setText(test[1])
            etLiters.setText(test[2])
            etPrice.setText(test[3])
        }

        etDate.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val intent = Intent(this, DateSelectActivity::class.java)
                activityResultLauncher.launch(intent)
                true // Consume the event
            } else {
                false // Allow other touch processing (e.g., focus)
            }
        }

        buttonRecord.setOnClickListener {
            if (etOdo.text.toString() != "" && etLiters.text.toString() != "" && etPrice.text.toString() != "") {
                edit.putStringSet(position.toString(), setOf("${etDate.text.toString()},${etOdo.text.toString()},${etLiters.text.toString()},${etPrice.text.toString()}"))
                edit.apply()

                etOdo.setText("")
                etLiters.setText("")
                etPrice.setText("")

                setResult(RESULT_OK)
                finish()
            }
        }

    }

    private val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Get the result data if needed
            val data = result.data
            if (data != null) {
                etDate.setText(data.getStringExtra("date"))
            }
        }
    }
}