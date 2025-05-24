package com.example.mctracker

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Tutorials
        //https://www.youtube.com/watch?v=-vAI7RSPxOA
        //https://www.youtube.com/watch?v=c43_GieHW3U

        val buttonRecord = findViewById<Button>(R.id.buttonRecord)
        val buttonHistory = findViewById<Button>(R.id.buttonHistory)
        val buttonData = findViewById<Button>(R.id.buttonData)


        buttonRecord.setOnClickListener {
            //Toast.makeText(this, "Record", Toast.LENGTH_SHORT).show()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView2, Recording())
            fragmentTransaction.commit()

        }

        buttonHistory.setOnClickListener {
            //Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView2, History())
            fragmentTransaction.commit()
        }

        buttonData.setOnClickListener{
            Toast.makeText(this, "Not yet available.", Toast.LENGTH_SHORT).show()
        }

    }
}