package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.dndcharactersheetmanager.models.characterSheet
import com.example.dndcharactersheetmanager.adapter.CharacterClassAdapter
import com.example.dndcharactersheetmanager.apiCalls.ClassListResponse
import com.example.dndcharactersheetmanager.apiCalls.ClassSummary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dndcharactersheetmanager.apiCalls.ClassDetails
import com.example.dndcharactersheetmanager.data.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CharacterCreation1 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterClassAdapter
    private lateinit var nextButton: ImageButton
    private lateinit var exitButton: Button

    private lateinit var selectedClass: ClassSummary

    private lateinit var characterSheet: characterSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creation_first)
        characterSheet = characterSheet()
        recyclerView = findViewById(R.id.classRecyclerView)
        nextButton = findViewById(R.id.nextButton)
        exitButton = findViewById(R.id.exitButton)

        // Disable next until a class is selected
        nextButton.isEnabled = false

        recyclerView.layoutManager = LinearLayoutManager(this)

        getDnDClasses { classList ->
            runOnUiThread {
                adapter = CharacterClassAdapter(classList) { selected ->
                    selectedClass = selected
                    nextButton.isEnabled = true
                    getClassDetails(index = selected.index) { details ->
                        characterSheet.characterClass = details
                    }
                }
            }
            recyclerView.adapter = adapter
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, CharacterCreation2::class.java)
            intent.putExtra(
                "character_sheet",
                characterSheet
            ) // pass the character sheet to the next screen
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            val viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
            CoroutineScope(Dispatchers.IO).launch {
                characterSheet.let {
                    viewModel.deleteCharacter(it)
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getDnDClasses(callback: (List<ClassSummary>) -> Unit) {
        val call = RetrofitInstance.api.getClasses()

        call.enqueue(object : retrofit2.Callback<ClassListResponse> {
            override fun onResponse(
                call: Call<ClassListResponse>,
                response: Response<ClassListResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.results ?: emptyList())
                } else {
                    Log.e("DND_API", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ClassListResponse>, t: Throwable) {
                Log.e("DND_API", "Network Error: ${t.message}")
            }
        })
    }
}

private fun getClassDetails(index: String, callback: (ClassDetails) -> Unit) {
    val call = RetrofitInstance.api.getClassDetails(index)

    call.enqueue(object : Callback<ClassDetails> {
        override fun onResponse(
            call: Call<ClassDetails>,
            response: Response<ClassDetails>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { callback(it) }
            } else {
                Log.e("DND_API", "Response error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ClassDetails>, t: Throwable) {
            Log.e("DND_API", "Network Error: ${t.message}")
        }
    })
}


