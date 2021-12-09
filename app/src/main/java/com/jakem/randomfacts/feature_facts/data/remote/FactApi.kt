package com.jakem.randomfacts.feature_facts.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FactApi {

    companion object {
        const val BASE_URL = "http://numbersapi.com"
    }

    @GET("/{start}..{end}/trivia?json")
    suspend fun getFactForNumbers(
        @Path("start") start: Int,
        @Path("end") end: Int = start
    ): Response<Map<Int, String>>

    @GET("/{start}..{end}/year/?json")
    suspend fun getFactForYears(
        @Path("start") start: Int,
        @Path("end") end: Int = start
    ): Response<Map<Int, String>>
}