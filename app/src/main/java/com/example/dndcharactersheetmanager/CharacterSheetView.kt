package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dndcharactersheetmanager.data.AppDatabase
import com.example.dndcharactersheetmanager.data.CharacterViewModel
import com.example.dndcharactersheetmanager.models.characterSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterSheetView : AppCompatActivity() {
    private lateinit var characterSheet: characterSheet
    private lateinit var viewModel: CharacterViewModel

    private lateinit var name: TextView
    private lateinit var characterClass: TextView
    private lateinit var race: TextView
    private lateinit var level: TextView
    private lateinit var currentHP: EditText
    private lateinit var totalHP: TextView
    private lateinit var armourClass: TextView
    private lateinit var strength: TextView
    private lateinit var dexterity: TextView
    private lateinit var constitution: TextView
    private lateinit var intelligence: TextView
    private lateinit var wisdom: TextView
    private lateinit var charisma: TextView
    private lateinit var homeButton: Button
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)

        characterSheet = intent.getParcelableExtra("character_sheet") ?: characterSheet()
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        name = findViewById(R.id.character_name)
        race = findViewById(R.id.character_race)
        characterClass = findViewById(R.id.character_class)
        currentHP = findViewById(R.id.current_hp)
        totalHP = findViewById(R.id.total_hp)
        level = findViewById(R.id.character_level)
        armourClass = findViewById(R.id.character_armour_class)
        strength = findViewById(R.id.strengthText)
        dexterity = findViewById(R.id.dexterityText)
        constitution = findViewById(R.id.constitutionText)
        intelligence = findViewById(R.id.intelligenceText)
        wisdom = findViewById(R.id.wisdomText)
        charisma = findViewById(R.id.charismaText)

        name.setText(characterSheet.name)
        race.setText(characterSheet.race?.name ?: "")
        characterClass.setText(characterSheet.characterClass?.name ?: "")
        currentHP.setText(characterSheet.hitPoints.toString())
        totalHP.setText("/${characterSheet.hitPoints}")
        level.setText("Level: ${characterSheet.level}")
        armourClass.setText("AC: ${characterSheet.armourClass}")
        strength.setText(characterSheet.strength.toString())
        dexterity.setText(characterSheet.dexterity.toString())
        constitution.setText(characterSheet.constitution.toString())
        intelligence.setText(characterSheet.intelligence.toString())
        wisdom.setText(characterSheet.wisdom.toString())
        charisma.setText(characterSheet.charisma.toString())

        homeButton = findViewById(R.id.homeButton)
        deleteButton = findViewById(R.id.deleteButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        val db = AppDatabase.getDatabase(this)
        val characterDao = db.characterDao()

        deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                characterSheet.let {
                    viewModel.deleteCharacter(it)
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}