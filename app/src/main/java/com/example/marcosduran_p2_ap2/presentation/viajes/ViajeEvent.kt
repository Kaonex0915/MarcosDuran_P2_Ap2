package com.example.marcosduran_p2_ap2.presentation.viajes

import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto

sealed interface ViajeEvent {
    data class ViajeChange(val viajeId: Int): ViajeEvent
    data class FechaChange(val fecha: String): ViajeEvent
    data class MillaChange(val millas: Int): ViajeEvent
    data class TasaDolar(val tasaDolar: Double): ViajeEvent
    data class MontoChange(val monto: Double): ViajeEvent
    data class ObsercacionesChange(val observaciones: String): ViajeEvent
    data object Save: ViajeEvent
    data class Delete(val viaje: ViajeDto): ViajeEvent
    data object New: ViajeEvent
    data class FindById(val viajeId: Int): ViajeEvent
    data object LoadViajes : ViajeEvent
}
