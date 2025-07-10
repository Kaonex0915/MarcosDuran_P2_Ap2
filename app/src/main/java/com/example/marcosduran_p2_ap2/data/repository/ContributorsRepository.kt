package com.example.marcosduran_p2_ap2.data.repository

import com.example.marcosduran_p2_ap2.data.remote.ContributorsApi
import com.example.marcosduran_p2_ap2.data.remote.Resource
import com.example.marcosduran_p2_ap2.data.remote.dto.ContributorsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException

class ContributorsRepository @Inject constructor(
    private val contributorsApi: ContributorsApi
) {

    fun getContributors(): Flow<Resource<List<ContributorsDto>>> = flow {
        try {
            emit(Resource.Loading())

            val contributors = contributorsApi.getContributors()

            emit(Resource.Success(contributors))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error al conectarse con la API"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error inesperado, verificar tu conexion a internet"))
        }
    }
}