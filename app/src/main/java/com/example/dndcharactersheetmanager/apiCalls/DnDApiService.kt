package com.example.dndcharactersheetmanager.apiCalls

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DnDApiService {
    // Get a list of all D&D classes
    @GET("api/classes")
    fun getClasses(): Call<ClassListResponse>

    // Get details about a specific class
    @GET("api/classes/{index}")
    fun getClassDetails(@Path("index") index: String): Call<ClassDetails>

    // Get a list of all D&D races
    @GET("api/races")
    fun getRaces(): Call<RaceListResponse>

    // Get details about a specific race
    @GET("api/races/{index}")
    fun getRaceDetails(@Path("index") index: String): Call<RaceDetailsResponse>
}