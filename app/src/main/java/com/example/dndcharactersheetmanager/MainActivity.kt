package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dndcharactersheetmanager.adapter.CharacterAdapter
import com.example.dndcharactersheetmanager.data.CharacterViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CharacterAdapter
    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        val recyclerView = findViewById<RecyclerView>(R.id.characterRecyclerView)
        val noCharactersText = findViewById<TextView>(R.id.noCharactersText)

        // Initially empty list — will be updated by LiveData
        adapter = CharacterAdapter(mutableListOf()) { character ->
            val intent = Intent(this, CharacterSheetView::class.java)
            intent.putExtra("character_sheet", character)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        viewModel.allCharacters.observe(this) { list ->
            adapter.updateData(list)
            noCharactersText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        val addCharacterButton = findViewById<FloatingActionButton>(R.id.addCharacterButton)
        addCharacterButton.setOnClickListener {
            val intent = Intent(this, CharacterCreation1::class.java)
            startActivity(intent)
        }
    }
}
