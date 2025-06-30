package com.example.dndcharactersheetmanager

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DnDApiService {
    // Get a list of all D&D classes
    @GET("api/classes")
    fun getClasses(): Call<ClassListResponse>

    // Get details about a specific class, like "barbarian"
    @GET("api/classes/{index}")
    fun getClassDetails(@Path("index") index: String): Call<ClassDetails>
}