package org.d3if0104.flightarchive.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0104.flightarchive.database.FlightDao
import org.d3if0104.flightarchive.model.Flight

class DetailViewModel(private val dao: FlightDao) : ViewModel() {

    fun insert(
        namaMaskapai: String,
        nomorPenerbangan: String,
        tanggalPenerbangan: String,
        jamPenerbangan: String,
        kelas: String,
        jenisPenerbangan: String
    ) {
        val flight = Flight(
            namaMaskapai = namaMaskapai,
            nomorPenerbangan = nomorPenerbangan,
            tanggalPenerbangan = tanggalPenerbangan,
            jamPenerbangan = jamPenerbangan,
            kelas = kelas,
            jenisPenerbangan = jenisPenerbangan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(flight)
        }
    }

    suspend fun getFlight(id: Long): Flight? {
        return dao.getFlightById(id)
    }

    fun update(
        id: Long,
        namaMaskapai: String,
        nomorPenerbangan: String,
        tanggalPenerbangan: String,
        jamPenerbangan: String,
        kelas: String,
        jenisPenerbangan: String
    ) {
        val flight = Flight(
            id = id,
            namaMaskapai = namaMaskapai,
            nomorPenerbangan = nomorPenerbangan,
            tanggalPenerbangan = tanggalPenerbangan,
            jamPenerbangan = jamPenerbangan,
            kelas = kelas,
            jenisPenerbangan = jenisPenerbangan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(flight)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }


}