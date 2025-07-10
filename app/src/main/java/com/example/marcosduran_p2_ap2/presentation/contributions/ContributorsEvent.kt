package com.example.marcosduran_p2_ap2.presentation.contributions

sealed interface ContributorsEvent {
    data class IdChange(val id: Int): ContributorsEvent
    data class loginChange(val login: String): ContributorsEvent
    data class avatarUrlChange(val avatarUrl: String): ContributorsEvent
    data class urlChange(val url: String): ContributorsEvent
    data class followersUrlChange(val followersUrl: String): ContributorsEvent
    data class followingUrlChange(val followingUrl: String): ContributorsEvent
    data class contributionsChange(val contributions: Int): ContributorsEvent
    data object LoadContributors : ContributorsEvent
}