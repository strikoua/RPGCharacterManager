package com.example.dndcharactersheetmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.*
import com.example.dndcharactersheetmanager.apiCalls.ClassDetails
import com.example.dndcharactersheetmanager.apiCalls.RaceDetailsResponse
import kotlin.math.floor

@Parcelize
@Entity(tableName = "characters")
data class characterSheet (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var characterClass: ClassDetails? = null,
    var race: RaceDetailsResponse? = null,
    var level: Int = 1,
    var armourClass: Int = 10,
    var strength: Int = 10,
    var dexterity: Int = 10,
    var constitution: Int = 10,
    var intelligence: Int = 10,
    var wisdom: Int = 10,
    var charisma: Int = 10
) : Parcelable {
    init {
        require(name.length <= 50)
        require(level in 1..20)
        require(armourClass in 5..50)
        require(strength in 1..30)
        require(dexterity in 1..30)
        require(constitution in 1..30)
        require(intelligence in 1..30)
        require(wisdom in 1..30)
        require(charisma in 1..30)
    }
    val hitDie: Int?
        get() = characterClass?.hit_die
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
        get() = (characterClass?.hit_die ?: 0) + (((characterClass?.hit_die ?: 0) / 2) + 1) * (level - 1) + (con_mod * level)
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