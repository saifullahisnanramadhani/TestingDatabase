package com.example.utsmobile2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface PrayerDao {

    @Insert
    suspend fun insert(prayer: PrayerRecord): Long

    @Delete
    suspend fun delete(prayer: PrayerRecord)

    @Query("SELECT * FROM prayer_table WHERE id = :id")
    suspend fun findById(id: Int): PrayerRecord?

    @Query("SELECT * FROM prayer_table")
    suspend fun getAll(): List<PrayerRecord>
}
