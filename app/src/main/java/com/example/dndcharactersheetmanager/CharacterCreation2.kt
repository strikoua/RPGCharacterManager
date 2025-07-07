package com.example.dndcharactersheetmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dndcharactersheetmanager.adapter.CharacterRaceAdapter
import com.example.dndcharactersheetmanager.apiCalls.RaceDetailsResponse
import com.example.dndcharactersheetmanager.apiCalls.RaceListResponse
import com.example.dndcharactersheetmanager.apiCalls.RaceSummary
import com.example.dndcharactersheetmanager.data.CharacterViewModel
import com.example.dndcharactersheetmanager.models.characterSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterCreation2 : AppCompatActivity() {
    private lateinit var characterSheet: characterSheet
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterRaceAdapter
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: Button
    private lateinit var exitButton: Button

    private lateinit var selectedRace: RaceSummary


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creation_second)

        characterSheet = intent.getParcelableExtra("character_sheet") ?: characterSheet()

        // Find view
        recyclerView = findViewById(R.id.raceRecyclerView)

        // Find buttons
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        exitButton = findViewById(R.id.exitButton)

        // Disable next until a class is selected
        nextButton.isEnabled = false

        recyclerView.layoutManager = LinearLayoutManager(this)

        getDnDRace { raceList ->
            runOnUiThread {
                adapter = CharacterRaceAdapter(raceList) { selected ->
                    selectedRace = selected
                    nextButton.isEnabled = true
                    getRaceDetails(index = selected.index) { details ->
                        characterSheet?.race = details
                        Log.d("DND_API", "Race: ${characterSheet?.race?.name}")
                        Log.d(
                            "DND_API",
                            "Class: ${characterSheet?.characterClass?.name}, Hit Die: ${characterSheet?.characterClass?.hit_die}"
                        )


                    }
                }
            }
            recyclerView.adapter = adapter
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, CharacterCreation3::class.java)
            intent.putExtra(
                "character_sheet",
                characterSheet
            ) // pass the character sheet to the next screen
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, CharacterCreation1::class.java)
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

    private fun getDnDRace(callback: (List<RaceSummary>) -> Unit) {
        val call = RetrofitInstance.api.getRaces()

        call.enqueue(object : retrofit2.Callback<RaceListResponse> {
            override fun onResponse(
                call: Call<RaceListResponse>,
                response: Response<RaceListResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.results ?: emptyList())
                } else {
                    Log.e("DND_API", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RaceListResponse>, t: Throwable) {
                Log.e("DND_API", "Network Error: ${t.message}")
            }
        })

    }

    private fun getRaceDetails(index: String, callback: (RaceDetailsResponse) -> Unit) {
        val call = RetrofitInstance.api.getRaceDetails(index)

        call.enqueue(object : Callback<RaceDetailsResponse> {
            override fun onResponse(
                call: Call<RaceDetailsResponse>,
                response: Response<RaceDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback(it) }
                } else {
                    Log.e("DND_API", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RaceDetailsResponse>, t: Throwable) {
                Log.e("DND_API", "Network Error: ${t.message}")
            }
        })
    }
}