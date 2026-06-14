package com.example.tandem.data

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface for the Tandem community API.
 * Each function maps to one endpoint.
 */
interface CommunityApiService {

    @GET("community_{page}.json")
    suspend fun getCommunityPage(
        @Path("page") page: Int
    ): CommunityResponse
}