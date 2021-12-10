package com.jakem.randomfacts.feature_facts.di

import android.app.Application
import androidx.room.Room
import com.jakem.randomfacts.feature_facts.data.local.FactDatabase
import com.jakem.randomfacts.feature_facts.data.remote.FactApi
import com.jakem.randomfacts.feature_facts.data.repository.FactRepositoryImpl
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetNumberFactsUseCase
import com.jakem.randomfacts.feature_facts.domain.use_cases.GetYearFactUseCase
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
    fun provideFactDatabase(app: Application): FactDatabase {
        return Room.databaseBuilder(app, FactDatabase::class.java, FactDatabase.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideFactRepository(
        api: FactApi,
        db: FactDatabase
    ): FactRepository {
        return FactRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideGetNumberFactsUseCase(
        repository: FactRepository
    ): GetNumberFactsUseCase {
        return GetNumberFactsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetYearFactUseCase(
        repository: FactRepository
    ): GetYearFactUseCase {
        return GetYearFactUseCase(repository)
    }

}