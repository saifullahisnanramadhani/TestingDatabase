package com.example.utsmobile2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrayerDaoTest {

    private lateinit var db: PrayerDatabase
    private lateinit var dao: PrayerDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            PrayerDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = db.prayerDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetAll_returnsCorrectData() = runBlocking {
        val prayer = PrayerRecord(tanggal = "2024-05-08", waktu = "05:00", status = "Sudah")
        val id = dao.insert(prayer) // Dapatkan ID

        val result = dao.getAll()
        assertEquals(1, result.size)
        assertEquals("2024-05-08", result[0].tanggal)
        assertEquals("05:00", result[0].waktu)
        assertEquals("Sudah", result[0].status)
    }

    @Test
    fun insertMultipleData_andGetAll() = runBlocking {
        val prayer1 = PrayerRecord(tanggal = "2024-05-08", waktu = "05:00", status = "Sudah")
        val prayer2 = PrayerRecord(tanggal = "2024-05-09", waktu = "12:00", status = "Belum")

        dao.insert(prayer1)
        dao.insert(prayer2)

        val result = dao.getAll()
        assertEquals(2, result.size)
    }

    @Test
    fun deleteData_andGetAll() = runBlocking {
        val prayer = PrayerRecord(tanggal = "2024-05-08", waktu = "05:00", status = "Sudah")
        val id = dao.insert(prayer)

        // Ambil objek dari database dengan ID yang sama
        val fetchedPrayer = dao.findById(id.toInt())
        assertNotNull(fetchedPrayer)

        // Hapus objek yang ditemukan
        dao.delete(fetchedPrayer!!)

        val result = dao.getAll()
        assertTrue(result.isEmpty())
    }

    @Test
    fun findById_returnsCorrectData() = runBlocking {
        val prayer = PrayerRecord(tanggal = "2024-05-08", waktu = "05:00", status = "Sudah")
        val id = dao.insert(prayer)

        val result = dao.findById(id.toInt())
        assertNotNull(result)
        assertEquals("2024-05-08", result?.tanggal)
        assertEquals("05:00", result?.waktu)
        assertEquals("Sudah", result?.status)
    }
}
