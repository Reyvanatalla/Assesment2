package org.d3if0104.flightarchive.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0104.flightarchive.database.FlightDao
import org.d3if0104.flightarchive.model.Flight


class MainViewModel(dao: FlightDao) : ViewModel() {

    val data: StateFlow<List<Flight>> = dao.getFlight().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}