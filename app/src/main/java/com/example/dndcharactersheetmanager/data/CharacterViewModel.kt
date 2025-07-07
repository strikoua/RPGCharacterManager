package com.example.dndcharactersheetmanager.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dndcharactersheetmanager.models.characterSheet
import kotlinx.coroutines.launch

class CharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).characterDao()

    val allCharacters: LiveData<List<characterSheet>> = dao.getAll()

    fun addCharacter(character: characterSheet) = viewModelScope.launch {
        dao.insert(character)
    }

    fun updateCharacter(character: characterSheet) = viewModelScope.launch {
        dao.update(character)
    }

    fun deleteCharacter(character: characterSheet) = viewModelScope.launch {
        dao.delete(character)
    }
}
