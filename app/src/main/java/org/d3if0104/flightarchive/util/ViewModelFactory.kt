package org.d3if0104.flightarchive.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0104.flightarchive.database.FlightDao

import org.d3if0104.flightarchive.ui.screen.DetailViewModel
import org.d3if0104.flightarchive.ui.screen.MainViewModel

class ViewModelFactory(
    private val dao: FlightDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dao) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}