package com.example.marcosduran_p2_ap2.data.remote.dto

data class ContributorsDto(
    val id: Int? = null,
    val login: String = "",
    val avatarUrl: String = "",
    val url: String = "",
    val followersUrl: String = "",
    val followingUrl: String = "",
    val contributions: Int? = 0
)