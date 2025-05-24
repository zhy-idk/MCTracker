package com.example.mctracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class History : Fragment(R.layout.fragment_history) {
    val odoList: ArrayList<HistoryItem> = ArrayList()
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
    }

    private val editActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            odoList.clear()
            load()
        }
    }

    fun load() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        recyclerView = view?.findViewById(R.id.recyclerView)!!

        val size = sharedPreferences.getAll().size

        if (size == odoList.size) {
            Log.d("size_check", "test")
            Log.d("size_check", "${size.toString()}, ${odoList.size.toString()}")
        } else {
            Log.d("size_check", "${size.toString()}, ${odoList.size.toString()}")
        }

        if (size != odoList.size) {
            for (i in 0 until size) {
                val testing =
                    sharedPreferences.getStringSet(i.toString(), setOf("0,none,none,none"))
                testing?.forEach { item ->
                    val test = item.split(",")
                    Log.d("test_in", "${size.toString()} ${i}")
                    odoList.add(i, HistoryItem(test[0], test[1], test[2], test[3]))
                }
            }


            // Set LayoutManager
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            val adapter = MyAdapter(odoList)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                    val intent = Intent(requireContext(), EditActivity::class.java)
                    intent.putExtra("position", position)
                    editActivityLauncher.launch(intent)
                }
            })
        }
    }
}
