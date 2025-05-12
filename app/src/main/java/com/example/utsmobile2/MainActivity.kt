package com.example.utsmobile2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tanggalEditText = findViewById<EditText>(R.id.editTextTanggal)
        val waktuEditText = findViewById<EditText>(R.id.editTextWaktu)
        val statusEditText = findViewById<EditText>(R.id.editTextStatus)
        val saveButton = findViewById<Button>(R.id.buttonSavePrayer)

        val db = PrayerDatabase.getInstance(applicationContext)

        saveButton.setOnClickListener {
            val tanggal = tanggalEditText.text.toString().trim()
            val waktu = waktuEditText.text.toString().trim()
            val status = statusEditText.text.toString().trim()

            // Validasi inputan
            if (tanggal.isNotEmpty() && waktu.isNotEmpty() && status.isNotEmpty()) {
                // Menggunakan coroutine untuk memanggil insert() karena itu adalah suspend function
                GlobalScope.launch {
                    val record = PrayerRecord(tanggal = tanggal, waktu = waktu, status = status)

                    // Menyimpan data ke database
                    db.prayerDao().insert(record)

                    // Kembali ke main thread untuk menampilkan hasil
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }

                    // Logging data yang ada di database
                    val allRecords = db.prayerDao().getAll()
                    Log.d("DB", "Semua data: $allRecords")
                }
            } else {
                // Memberikan feedback jika ada kolom yang kosong
                Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
