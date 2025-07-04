package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class CharacterCreation3 : AppCompatActivity() {

    private lateinit var characterSheet: characterSheet

    private lateinit var levelEditText: EditText
    private lateinit var levelErrorText: TextView
    private lateinit var acEditText: EditText
    private lateinit var acErrorText: TextView
    private lateinit var strengthEditText: EditText
    private lateinit var strengthErrorText: TextView
    private lateinit var dexterityEditText: EditText
    private lateinit var dexterityErrorText: TextView
    private lateinit var constitutionEditText: EditText
    private lateinit var constitutionErrorText: TextView
    private lateinit var intelligenceEditText: EditText
    private lateinit var intelligenceErrorText: TextView
    private lateinit var wisdomEditText: EditText
    private lateinit var wisdomErrorText: TextView
    private lateinit var charismaEditText: EditText
    private lateinit var charismaErrorText: TextView

    private lateinit var nextButton: ImageButton
    private lateinit var backButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creation_third)

        characterSheet = intent.getParcelableExtra("characterSheet") ?: characterSheet()

        // Find views
        levelEditText = findViewById(R.id.levelEditText)
        levelErrorText = findViewById(R.id.levelErrorText)
        acEditText = findViewById(R.id.acEditText)
        acErrorText = findViewById(R.id.acErrorText)
        strengthEditText = findViewById(R.id.strengthEditText)
        strengthErrorText = findViewById(R.id.strengthErrorText)
        dexterityEditText = findViewById(R.id.dexterityEditText)
        dexterityErrorText = findViewById(R.id.dexterityErrorText)
        constitutionEditText = findViewById(R.id.constitutionEditText)
        constitutionErrorText = findViewById(R.id.constitutionErrorText)
        intelligenceEditText = findViewById(R.id.intelligenceEditText)
        intelligenceErrorText = findViewById(R.id.intelligenceErrorText)
        wisdomEditText = findViewById(R.id.wisdomEditText)
        wisdomErrorText = findViewById(R.id.wisdomErrorText)
        charismaEditText = findViewById(R.id.charismaEditText)
        charismaErrorText = findViewById(R.id.charismaErrorText)

        // Find buttons
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        exitButton = findViewById(R.id.exitButton)

        fillFields()

        nextButton.setOnClickListener {
            var isValid = validateAllFields()
            if (isValid) {
                characterSheet.level = levelEditText.text.toString().toInt()
                characterSheet.armourClass = acEditText.text.toString().toInt()
                characterSheet.strength = strengthEditText.text.toString().toInt()
                characterSheet.dexterity = dexterityEditText.text.toString().toInt()
                characterSheet.constitution = constitutionEditText.text.toString().toInt()
                characterSheet.intelligence = intelligenceEditText.text.toString().toInt()
                characterSheet.wisdom = wisdomEditText.text.toString().toInt()
                characterSheet.charisma = charismaEditText.text.toString().toInt()
                Log.d("DND_API", "level: ${characterSheet.level}")
                Log.d("DND_API", "ac: ${characterSheet.armourClass}")
                Log.d("DND_API", "strength: ${characterSheet.strength}")
                Log.d("DND_API", "str mod: ${characterSheet.str_mod}")
//                val intent = Intent (this, CharacterCreation2::class.java)
//                intent.putExtra("character_sheet", characterSheet) // pass the character sheet to the next screen
//                startActivity(intent)
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

    private fun fillFields() {
        levelEditText.setText(characterSheet.level.toString())
        acEditText.setText(characterSheet.armourClass.toString())
        strengthEditText.setText(characterSheet.strength.toString())
        dexterityEditText.setText(characterSheet.dexterity.toString())
        constitutionEditText.setText(characterSheet.constitution.toString())
        intelligenceEditText.setText(characterSheet.intelligence.toString())
        wisdomEditText.setText(characterSheet.wisdom.toString())
        charismaEditText.setText(characterSheet.charisma.toString())
    }


    private fun showError(editText: EditText, errorText: TextView) {
        editText.setBackgroundResource(R.drawable.error_edit_text_background)
        errorText.visibility = View.VISIBLE
    }

    private fun hideError(editText: EditText, errorText: TextView) {
        editText.setBackgroundResource(R.drawable.edit_text_background)
        errorText.visibility = View.GONE
    }

    private fun validateAllFields(): Boolean {
        var isValid = true

        // Validate each field
        val level = levelEditText.text.toString().toIntOrNull()
        if (level == null || level !in 1..20) {
            showError(levelEditText, levelErrorText)
            isValid = false
        }
        else hideError(levelEditText, levelErrorText)

        val ac = acEditText.text.toString().toIntOrNull()
        if (level == null || ac !in 5..50) {
            showError(acEditText, acErrorText)
            isValid = false
        }
        else hideError(acEditText, acErrorText)

        val strength = strengthEditText.text.toString().toIntOrNull()
        if (level == null || strength !in 1..30) {
            showError(strengthEditText, strengthErrorText)
            isValid = false
        }
        else hideError(strengthEditText, strengthErrorText)

        val dexterity = dexterityEditText.text.toString().toIntOrNull()
        if (level == null || dexterity !in 1..30) {
            showError(dexterityEditText, dexterityErrorText)
            isValid = false
        }
        else hideError(dexterityEditText, dexterityErrorText)

        val constitution = constitutionEditText.text.toString().toIntOrNull()
        if (level == null || constitution !in 1..30) {
            showError(constitutionEditText, constitutionErrorText)
            isValid = false
        }
        else hideError(constitutionEditText, constitutionErrorText)

        val intelligence = intelligenceEditText.text.toString().toIntOrNull()
        if (level == null || intelligence !in 1..30) {
            showError(intelligenceEditText, intelligenceErrorText)
            isValid = false
        }
        else hideError(intelligenceEditText, intelligenceErrorText)

        val wisdom = wisdomEditText.text.toString().toIntOrNull()
        if (level == null || wisdom !in 1..30) {
            showError(wisdomEditText, wisdomErrorText)
            isValid = false
        }
        else hideError(wisdomEditText, wisdomErrorText)

        val charisma = charismaEditText.text.toString().toIntOrNull()
        if (level == null || charisma !in 1..30) {
            showError(charismaEditText, charismaErrorText)
            isValid = false
        }
        else hideError(charismaEditText, charismaErrorText)

        return isValid
    }
}


