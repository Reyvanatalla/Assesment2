package org.d3if0104.flightarchive.navigation

import org.d3if0104.flightarchive.ui.screen.KEY_ID_FLIGHT

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_FLIGHT}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}