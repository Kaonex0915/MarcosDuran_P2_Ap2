package com.example.marcosduran_p2_ap2.presentation.navigation

import android.adservices.adid.AdId
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ViajeList: Screen()

    @Serializable
    data class Viaje(val viajeId: Int?) : Screen()
}
