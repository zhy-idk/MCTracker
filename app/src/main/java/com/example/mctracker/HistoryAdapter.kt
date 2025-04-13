package com.example.mctracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val examList: MutableList<HistoryItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val examItem = examList[position]

        holder.refuelDate.text = examItem.refuelDate
        holder.distance.text = examItem.distance
        holder.fuelAmount.text = examItem.fuelAmount


    }

    override fun getItemCount(): Int {
        return examList.size
    }

    // ViewHolder class
    class MyViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val refuelDate: TextView = itemView.findViewById(R.id.refuelDate)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val fuelAmount: TextView = itemView.findViewById(R.id.fuelAmount)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(getBindingAdapterPosition())
            }
        }
    }
}
