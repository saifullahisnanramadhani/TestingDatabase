package com.example.utsmobile2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_table")
data class PrayerRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tanggal: String,
    val waktu: String,
    val status: String
)
