package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dndcharactersheetmanager.data.AppDatabase
import com.example.dndcharactersheetmanager.models.characterSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterCreation4 : AppCompatActivity() {
    private lateinit var characterSheet: characterSheet

    private lateinit var nameInputEditText: EditText
    private lateinit var nameErrorText: TextView

    private lateinit var nextButton: ImageButton
    private lateinit var backButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creation_fourth)

        // Find views
        nameInputEditText = findViewById(R.id.nameEditText)
        nameErrorText = findViewById(R.id.nameErrorText)

        // Find buttons
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        exitButton = findViewById(R.id.exitButton)

        characterSheet = intent.getParcelableExtra("character_sheet") ?: characterSheet()

        nextButton.setOnClickListener {
            var isValid = validateName()
            if (isValid) {
                characterSheet.name = nameInputEditText.text.toString()
                Log.d("DND_API", "name: ${characterSheet.name}")
                Log.d("DND_API", "race: ${characterSheet.race?.name}")
                val intent = Intent (this, CharacterSheetView::class.java)
                intent.putExtra("character_sheet", characterSheet) // pass the character sheet to the next screen
                startActivity(intent)
            }
        }

        backButton.setOnClickListener {
            val intent = Intent (this, CharacterCreation1::class.java)
            startActivity(intent)
        }

        val db = AppDatabase.getDatabase(this)
        val characterDao = db.characterDao()

        exitButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                characterSheet.let {
                    characterDao.delete(it)
                }
            }
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showError(editText: EditText, errorText: TextView) {
        editText.setBackgroundResource(R.drawable.error_edit_text_background)
        errorText.visibility = View.VISIBLE
    }

    private fun hideError(editText: EditText, errorText: TextView) {
        editText.setBackgroundResource(R.drawable.edit_text_background)
        errorText.visibility = View.GONE
    }

    private fun validateName(): Boolean {
        var isValid = true

        val name = nameInputEditText.text.toString()
        if (name.length !in 1..50) {
            showError(nameInputEditText, nameErrorText)
            isValid = false
        } else {
            hideError(nameInputEditText, nameErrorText)
        }

        return isValid
    }

}