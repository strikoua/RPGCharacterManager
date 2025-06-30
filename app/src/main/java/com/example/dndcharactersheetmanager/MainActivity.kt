package com.example.dndcharactersheetmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dndcharactersheetmanager.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        getDnDRaces()
    }

    private fun getDnDClasses() {
        val call = RetrofitInstance.api.getClasses()

        call.enqueue(object : Callback<ClassListResponse> {
            override fun onResponse(
                call: Call<ClassListResponse>,
                response: Response<ClassListResponse>
            ) {
                if (response.isSuccessful) {
                    val classList = response.body()
                    Log.d("DND_API", "Success! Got ${classList?.count} classes")
                    classList?.results?.forEach {
                        Log.d("DND_API", "Class: ${it.name}, Index: ${it.index}")
                    }
                } else {
                    Log.e("DND_API", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ClassListResponse>, t: Throwable) {
                Log.e("DND_API", "Network Error: ${t.message}")
            }
        })
    }

    private fun getClassDetails(index: String) {
        val call = RetrofitInstance.api.getClassDetails(index)

        call.enqueue(object : Callback<ClassDetails> {
            override fun onResponse(
                call: Call<ClassDetails>,
                response: Response<ClassDetails>
            ) {
                if (response.isSuccessful) {
                    val classDetails = response.body()
                    Log.d("DND_API", "Class: ${classDetails?.name}, Hit Die: ${classDetails?.hit_die}")
                }
            }

            override fun onFailure(call: Call<ClassDetails>, t: Throwable) {
                println("Error loading details: ${t.message}")
            }
        })
    }

    private fun getDnDRaces() {
        val call = RetrofitInstance.api.getRaces()

        call.enqueue(object : Callback<RaceListResponse> {
            override fun onResponse(
                call: Call<RaceListResponse>,
                response: Response<RaceListResponse>
            ) {
                if (response.isSuccessful) {
                    val raceList = response.body()
                    Log.d("DND_API", "Success! Got ${raceList?.count} classes")
                    raceList?.results?.forEach {
                        Log.d("DND_API", "Race: ${it.name}, Index: ${it.index}")
                    }
                } else {
                    Log.e("DND_API", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RaceListResponse>, t: Throwable) {
                Log.e("DND_API", "Network Error: ${t.message}")
            }
        })
    }

}


