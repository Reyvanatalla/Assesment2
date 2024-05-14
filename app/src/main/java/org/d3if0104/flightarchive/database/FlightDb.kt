package org.d3if0104.flightarchive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0104.flightarchive.model.Flight

@Database(entities = [Flight::class], version = 1, exportSchema = false)
abstract class FlightDb: RoomDatabase() {
    abstract val dao: FlightDao


    companion object{
        @Volatile
        private var INSTANCE: FlightDb? = null

        fun getInstance(context: Context): FlightDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlightDb::class.java,
                        "flight.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}