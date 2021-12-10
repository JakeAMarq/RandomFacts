package com.jakem.randomfacts.feature_facts.domain.repository

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    fun getNumberFacts(
        start: Int,
        end: Int = start
    ): Flow<Resource<List<Fact>>>

    fun getYearFact(
        year: Int
    ): Flow<Resource<Fact>>
}