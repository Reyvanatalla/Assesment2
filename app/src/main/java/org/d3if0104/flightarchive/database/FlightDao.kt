package org.d3if0104.flightarchive.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

import org.d3if0104.flightarchive.model.Flight

@Dao
interface FlightDao {
    @Insert
    suspend fun insert(flight: Flight)

    @Update
    suspend fun update(flight: Flight)

    @Query("SELECT * FROM flight ORDER BY jenisPenerbangan, kelas, jamPenerbangan, tanggalPenerbangan, nomorPenerbangan, namaMaskapai ASC")
    fun getFlight(): Flow<List<Flight>>

    @Query("SELECT * FROM flight WHERE id = :id")
    suspend fun getFlightById(id: Long): Flight?

    @Query("DELETE FROM flight WHERE id = :id")
    suspend fun deleteById(id: Long)
}