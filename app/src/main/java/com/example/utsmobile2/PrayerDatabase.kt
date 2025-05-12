package com.example.utsmobile2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PrayerRecord::class], version = 1, exportSchema = false)
abstract class PrayerDatabase : RoomDatabase() {

    abstract fun prayerDao(): PrayerDao

    companion object {
        @Volatile
        private var INSTANCE: PrayerDatabase? = null

        fun getInstance(context: Context): PrayerDatabase {
            // Return the existing database or create a new one
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrayerDatabase::class.java,
                    "prayer_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
