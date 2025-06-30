package com.example.dndcharactersheetmanager.models

import androidx.room.*
import com.example.dndcharactersheetmanager.apiCalls.ClassDetails
import com.example.dndcharactersheetmanager.apiCalls.RaceDetailsResponse
import kotlin.math.floor

@Entity(tableName = "characters")
data class characterSheet (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    val characterClass: ClassDetails,
    val race: RaceDetailsResponse,
    var level: Int,
    var armourClass: Int,
    var strength: Int,
    var dexterity: Int,
    var constitution: Int,
    var intelligence: Int,
    var wisdom: Int,
    var charisma: Int
) {
    init {
        require(name.length <= 50)
        require(level in 1..20)
        require(strength in 1..30)
        require(dexterity in 1..30)
        require(constitution in 1..30)
        require(intelligence in 1..30)
        require(wisdom in 1..30)
        require(charisma in 1..30)
    }
    val hitDie: Int
        get() = characterClass.hit_die
    val str_mod: Int
        get() = floor((strength - 10).toDouble() / 2).toInt()
    val dex_mod: Int
        get() = floor((dexterity - 10).toDouble() / 2).toInt()
    val con_mod: Int
        get() = floor((constitution - 10).toDouble() / 2).toInt()
    val int_mod: Int
        get() = floor((intelligence - 10).toDouble() / 2).toInt()
    val wis_mod: Int
        get() = floor((wisdom - 10).toDouble() / 2).toInt()
    val cha_mod: Int
        get() = floor((charisma - 10).toDouble() / 2).toInt()
    val hitPoints: Int
        get() = characterClass.hit_die + ((characterClass.hit_die / 2) + 1) * (level - 1) + (con_mod * level)
    val proficiencyBonus: Int
        get() {
            when {
                level < 5 -> return 2
                level < 9 -> return 3
                level < 13 -> return 4
                level < 17 -> return 5
                level >= 17 -> return 6
                else -> return 2
            }
        }
}