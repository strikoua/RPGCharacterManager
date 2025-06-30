package com.example.dndcharactersheetmanager


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: DnDApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.dnd5eapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DnDApiService::class.java)
    }
}