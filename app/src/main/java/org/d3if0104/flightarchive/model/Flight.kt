package org.d3if0104.flightarchive.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flight")
data class Flight(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaMaskapai: String,
    val nomorPenerbangan: String,
    val tanggalPenerbangan: String,
    val jamPenerbangan: String,
    val kelas: String,
    val jenisPenerbangan: String
)