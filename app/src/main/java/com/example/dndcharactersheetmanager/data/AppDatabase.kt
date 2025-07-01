package com.example.dndcharactersheetmanager.data

import android.content.Context
import com.example.dndcharactersheetmanager.models.characterSheet
import androidx.room.*


@Database(entities = [characterSheet::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "character_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
