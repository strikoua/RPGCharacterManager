package com.example.dndcharactersheetmanager.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dndcharactersheetmanager.R
import com.example.dndcharactersheetmanager.models.characterSheet

class CharacterAdapter(
    private val characters: MutableList<characterSheet>,
    private val onItemClick: (characterSheet) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(character: characterSheet) {
            itemView.findViewById<TextView>(R.id.nameText).text = character.name
            itemView.findViewById<TextView>(R.id.classRaceText).text =
                "${character.race?.name} ${character.characterClass?.name}"
            itemView.findViewById<TextView>(R.id.levelText).text = "Level ${character.level}"
            itemView.setOnClickListener {
                onItemClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

    fun updateData(newCharacters: List<characterSheet>) {
        (characters as MutableList).clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }
}
