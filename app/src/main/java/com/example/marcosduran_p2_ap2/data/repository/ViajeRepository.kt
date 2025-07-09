package com.example.marcosduran_p2_ap2.data.repository

import ViajeApi
import android.adservices.adid.AdId
import android.net.http.HttpException
import com.example.marcosduran_p2_ap2.data.remote.Resource
import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViajeRepository @Inject constructor(
    private val viajeApi: ViajeApi
) {
    fun getViaje(): Flow<Resource<List<ViajeDto>>> = flow {
        try {
            emit(Resource.Loading())

            val viajes = ViajeApi.getViaje()

            emit(Resource.Success(viajes   ))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun getViajeById(viajeId: Int): Flow<Resource<ViajeDto>> = flow {
        try {
            emit(Resource.Loading())

            val response = ViajeApi.getViajeById(viajeId)

            emit(Resource.Success(response.body()!!))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun postViaje(viajeDto: ViajeDto): Flow<Resource<ViajeDto>> = flow{
        try {
            emit(Resource.Loading())

            ViajeApi.postViaje(viajeDto)
            emit(Resource.Success(viajeDto))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun deleteViaje(viajeId: AdId): Flow<Resource<ViajeDto>> = flow{
        try {
            emit(Resource.Loading())

            ViajeApi.deleteViaje(viajeId)
            emit(Resource.Success(ViajeDto()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }
}
