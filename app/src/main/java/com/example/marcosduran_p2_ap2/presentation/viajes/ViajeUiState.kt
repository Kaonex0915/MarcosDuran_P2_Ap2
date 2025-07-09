package com.example.marcosduran_p2_ap2.presentation.viajes

import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto

data class ViajeUiState(
    val idViaje: Int? = null,
    val fecha: String? = null,
    val millas: Int? = 0,
    val tasaDolar: Double = 0.0,
    val monto: Double = 0.0,
    val observacion: String = "",
    val errorMessage: String? = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val deposito: ViajeDto = ViajeDto(),
    val depositos: List<ViajeDto> = emptyList()
)
