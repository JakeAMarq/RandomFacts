package com.jakem.randomfacts.feature_facts.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: FactApi by lazy {
        Retrofit.Builder()
            .baseUrl(FactApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactApi::class.java)
    }

}