package com.example.marcosduran_p2_ap2.data.repository

import ViajeApi
import android.os.ext.SdkExtensions
import androidx.annotation.RequiresExtension
import com.example.marcosduran_p2_ap2.data.remote.Resource
import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException

class ViajeRepository @Inject constructor(
    private val viajeApi: ViajeApi
) {
    fun getviaje(): Flow<Resource<List<ViajeDto>>> = flow {
        try {
            emit(Resource.Loading())

            val viajes = viajeApi.getViaje()

            emit(Resource.Success(viajes   ))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun getviajeById(viajeId: Int): Flow<Resource<ViajeDto>> = flow {
        try {
            emit(Resource.Loading())

            val viajes = viajeApi.getViajeById(viajeId)

            emit(Resource.Success(viajes))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    fun postviaje(viajeDto: ViajeDto): Flow<Resource<ViajeDto>> = flow{
        try {
            emit(Resource.Loading())

            viajeApi.postViaje(viajeDto)
            emit(Resource.Success(viajeDto))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }

    @RequiresExtension(extension = SdkExtensions.AD_SERVICES, version = 4)
    fun deleteviaje(viajeId: Int?): Flow<Resource<ViajeDto>> = flow{
        try {
            emit(Resource.Loading())

            viajeApi.deleteViaje(viajeId)
            emit(Resource.Success(ViajeDto()))

        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }
}
