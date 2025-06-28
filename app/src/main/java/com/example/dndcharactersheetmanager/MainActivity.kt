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
        getDnDClasses()
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
                        Log.d("DND_API", "Class: ${it.name}")
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

}


