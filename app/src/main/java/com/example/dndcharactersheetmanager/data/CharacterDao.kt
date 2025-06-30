package com.example.dndcharactersheetmanager.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dndcharactersheetmanager.models.characterSheet


@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): LiveData<List<characterSheet>>

    @Insert
    suspend fun insert(character: characterSheet)

    @Update
    suspend fun update(character: characterSheet)

    @Delete
    suspend fun delete(character: characterSheet)
}