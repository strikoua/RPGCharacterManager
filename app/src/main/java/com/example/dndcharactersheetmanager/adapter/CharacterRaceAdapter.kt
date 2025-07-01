package com.example.dndcharactersheetmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dndcharactersheetmanager.adapter.CharacterClassAdapter.ClassViewHolder
import com.example.dndcharactersheetmanager.apiCalls.RaceSummary

class CharacterRaceAdapter (
    private val races:List<RaceSummary>,
    private val onItemClick: (RaceSummary) -> Unit
) : RecyclerView.Adapter<CharacterRaceAdapter.RaceViewHolder>(){
    var selectedPosition = RecyclerView.NO_POSITION

    inner class RaceViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                val previous = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previous)
                notifyItemChanged(selectedPosition)
                onItemClick(races[selectedPosition])
            }
        }
        val raceText: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val classItem = races[position]
        holder.raceText.text = classItem.name

        // Highlight selected item
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_blue_light)
            )
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun getItemCount(): Int = races.size
}