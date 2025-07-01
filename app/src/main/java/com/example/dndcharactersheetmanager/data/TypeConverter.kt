package com.example.dndcharactersheetmanager.data

import androidx.room.TypeConverter
import com.example.dndcharactersheetmanager.apiCalls.ClassDetails
import com.example.dndcharactersheetmanager.apiCalls.RaceDetailsResponse
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromClassDetails(classDetails: ClassDetails?): String? {
        return if (classDetails == null) null else gson.toJson(classDetails)
    }

    @TypeConverter
    fun toClassDetails(data: String?): ClassDetails? {
        return if (data == null) null else gson.fromJson(data, ClassDetails::class.java)
    }

    @TypeConverter
    fun fromRace(raceDetails: RaceDetailsResponse?): String? {
        return raceDetails?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toRace(raceString: String?): RaceDetailsResponse? {
        return raceString?.let {
            Gson().fromJson(it, RaceDetailsResponse::class.java)
        }
    }
}
