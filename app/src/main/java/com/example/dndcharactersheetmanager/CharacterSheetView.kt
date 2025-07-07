package com.example.dndcharactersheetmanager

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dndcharactersheetmanager.models.characterSheet

class CharacterSheetView :AppCompatActivity() {
    private lateinit var characterSheet: characterSheet
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

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)

        characterSheet = intent.getParcelableExtra("character_sheet") ?: characterSheet()
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

        Log.d("DND_API", "name: ${characterSheet.name}")
        Log.d("DND_API", "race: ${characterSheet.race?.name}")














    }
}