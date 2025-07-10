package com.example.marcosduran_p2_ap2.presentation.contributions

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcosduran_p2_ap2.data.remote.Resource
import com.example.marcosduran_p2_ap2.data.repository.ContributorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributorsViewModel @Inject constructor(
    private val contributorsRepository: ContributorsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ContributorsUiState())
    val state = _state.asStateFlow()


    private fun getContributors() {
        viewModelScope.launch {
            contributorsRepository.getContributors().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(contributors = result.data ?: emptyList(), isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(errorMessage = "Ha ocurrido un error, intente de nuevo..", isLoading = false)
                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: ContributorsEvent) {
        when (event) {
            is ContributorsEvent.IdChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(id = event.id)
                    )
                }
            }

            is ContributorsEvent.loginChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(login = event.login)
                    )
                }
            }

            is ContributorsEvent.avatarUrlChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(avatarUrl = event.avatarUrl)
                    )
                }
            }

            is ContributorsEvent.urlChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(url = event.url)
                    )
                }
            }

            is ContributorsEvent.followersUrlChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(followersUrl = event.followersUrl)
                    )
                }
            }

            is ContributorsEvent.followingUrlChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(followingUrl = event.followingUrl)
                    )
                }
            }

            is ContributorsEvent.contributionsChange -> {
                _state.update {
                    it.copy(
                        contributor = it.contributor.copy(contributions = event.contributions)
                    )
                }
            }

            ContributorsEvent.LoadContributors -> {
                getContributors()
            }
        }
    }
}