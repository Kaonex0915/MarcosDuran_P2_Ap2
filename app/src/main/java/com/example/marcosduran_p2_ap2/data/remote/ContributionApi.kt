package com.example.marcosduran_p2_ap2.data.remote

import com.example.marcosduran_p2_ap2.data.remote.dto.ContributorsDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface ContributorsApi {
    @GET("repos/enelramon/DemoAp2/contributors")
    @Headers("X-API-Key: test")
    suspend fun getContributors():List<ContributorsDto>
}