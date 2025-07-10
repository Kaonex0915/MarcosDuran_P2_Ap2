package com.example.marcosduran_p2_ap2.presentation.navigation


import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ContributorsList: Screen()
}
