package com.example.marcosduran_p2_ap2.presentation.contributions

import com.example.marcosduran_p2_ap2.data.remote.dto.ContributorsDto

data class ContributorsUiState(
    val id: Int = 0,
    val login: String = "",
    val avatarUrl: String = "",
    val url: String = "",
    val followersUrl: String = "",
    val followingUrl: String = "",
    val contributions: Int = 0,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val contributor: ContributorsDto = ContributorsDto(),
    val contributors: List<ContributorsDto> = emptyList()
)