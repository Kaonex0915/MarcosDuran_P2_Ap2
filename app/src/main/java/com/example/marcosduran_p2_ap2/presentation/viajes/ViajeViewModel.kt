package com.example.marcosduran_p2_ap2.presentation.viajes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcosduran_p2_ap2.data.remote.Resource
import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto
import com.example.marcosduran_p2_ap2.data.repository.ViajeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViajeViewModel @Inject constructor(
    private val viajeRepository: ViajeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ViajeUiState())
    val state = _state.asStateFlow()

    private fun getViaje() {
        viewModelScope.launch {
            try {
                viajeRepository.getViaje().collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true, errorMessage = null) }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    viajes = result.data ?: emptyList(),
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = result.message ?: "Error desconocido",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error inesperado: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun postViaje() {
        viewModelScope.launch {
            try {
                viajeRepository.postViaje(state.value.viaje).collectLatest { result ->
                    when(result){
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    successMessage = "Guardado correctamente",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = result.message ?: "Ocurrio un error, intentelo de nuevo",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error de conexión: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun deleteViaje(viajeId: Int?){
        viewModelScope.launch {
            viajeId?.let { id ->
                viajeRepository.deleteViaje(id).collectLatest { result ->
                    when(result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    successMessage = "Eliminado correctamente",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = "Ocurrió un error: ${result.message}",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun getViajeById(viajeId: Int?) {
        viewModelScope.launch {
            viajeRepository.getViajeById(viajeId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                viaje = result.data ?: ViajeDto(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = "Ocurrio un error, intentelo de nuevo",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: ViajeEvent) {
        when (event) {
            is ViajeEvent.ViajeChange -> {
                _state.update {
                    it.copy(
                        viaje = it.idViaje.copy(idViaje = event.viajeId)
                    )
                }
            }

            is ViajeEvent.FechaChange -> {
                _state.update {
                    it.copy(
                        viaje = it.idViaje.copy(fecha = event.fecha)
                    )
                }
            }

            is ViajeEvent.MillaChange -> {
                _state.update {
                    it.copy(
                        viaje = it.idViaje.copy(millas = event.millas)
                    )
                }
            }

            is ViajeEvent.ObsercacionesChange -> {
                _state.update {
                    it.copy(
                        viaje = it.idViaje.copy(obsercaciones = event.observaciones)
                    )
                }
            }

            is ViajeEvent.MontoChange -> {
                _state.update {
                    it.copy(
                        viaje = it.idViaje.copy(monto = event.monto)
                    )
                }
            }

            ViajeEvent().Save -> {
                if (_state.value.viaje.fecha == null) {
                    _state.update {
                        it.copy(
                            viaje = it.viaje.copy(fecha = getCurrentDateISO())
                        )
                    }
                }
                postViaje()
            }

            ViajeEvent().New -> {
                _state.update {
                    it.copy(
                        viaje = ViajeDto(fecha = getCurrentDateISO()),
                        successMessage = null,
                        errorMessage = "",
                        isLoading = false
                    )
                }
            }

            is ViajeEvent.Delete -> {
                deleteViaje(event.viaje.idViaje)
            }

            is ViajeEvent.FindById -> {
                getViajeById(event.viajeId)
            }

            ViajeEvent().LoadViajes -> {
                getViaje()
            }
        }
    }
}
