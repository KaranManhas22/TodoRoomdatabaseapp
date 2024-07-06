package com.karan.todoroomdatabaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(var array: ArrayList<ToDoEntity>, private var Interface: Interface) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        var btndel: Button = view.findViewById(R.id.btndelete)
        var btnupdate: Button = view.findViewById(R.id.btnupdate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = array[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.btndel.setOnClickListener {
            Interface.DeleteData(position)
        }
        holder.btnupdate.setOnClickListener {
            Interface.UpdateData(position)
        }
    }

}