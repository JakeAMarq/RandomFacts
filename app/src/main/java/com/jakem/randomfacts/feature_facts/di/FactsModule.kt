package com.jakem.randomfacts.feature_facts.di

import com.jakem.randomfacts.feature_facts.data.remote.FactApi
import com.jakem.randomfacts.feature_facts.data.repository.FactRepositoryImpl
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactsModule {

    @Provides
    @Singleton
    fun provideFactApi(): FactApi {
        return Retrofit.Builder()
            .baseUrl(FactApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFactRepository(
        api: FactApi
    ): FactRepository {
        return FactRepositoryImpl(api)
    }

}