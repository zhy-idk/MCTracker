package com.example.mctracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Recording : Fragment(R.layout.fragment_recording) {
    lateinit var etDate: EditText
    lateinit var etOdo: EditText
    lateinit var etLiters: EditText
    lateinit var etPrice: EditText
    lateinit var buttonRecord: Button

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etDate = view.findViewById(R.id.etDate)
        etOdo = view.findViewById(R.id.etOdo)
        etLiters = view.findViewById(R.id.etLiters)
        etPrice = view.findViewById(R.id.etPrice)
        buttonRecord = view.findViewById(R.id.buttonRecord)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val size = sharedPreferences.getAll().size.toString()
        val edit = sharedPreferences.edit()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val current = LocalDateTime.now().format(formatter)

        etDate.setText(current.toString())

        etDate.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val intent = Intent(requireContext(), DateSelectActivity::class.java)
                activityResultLauncher.launch(intent)
                true // Consume the event
            } else {
                false // Allow other touch processing (e.g., focus)
            }
        }

        buttonRecord.setOnClickListener {
            if (etOdo.text.toString() != "" && etLiters.text.toString() != "" && etPrice.text.toString() != "") {
                edit.putStringSet(size, setOf("${etDate.text.toString()},${etOdo.text.toString()},${etLiters.text.toString()},${etPrice.text.toString()}"))
                edit.apply()

                etOdo.setText("")
                etLiters.setText("")
                etPrice.setText("")

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