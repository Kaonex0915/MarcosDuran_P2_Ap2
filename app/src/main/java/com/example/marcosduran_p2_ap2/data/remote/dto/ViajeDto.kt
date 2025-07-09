package com.example.marcosduran_p2_ap2.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Viaje")
data class ViajeDto (
    @PrimaryKey
    val idViaje: Int? = 0,
    val fecha: String? = null,
    val millas: Int? = null,
    val concepto: String = "",
    val tasaDolar: Double = 0.0,
    val monto: Double = 0.0,
    val observaciones : String = ""
)

