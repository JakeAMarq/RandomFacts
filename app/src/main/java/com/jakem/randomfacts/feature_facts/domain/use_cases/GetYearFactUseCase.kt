package com.jakem.randomfacts.feature_facts.domain.use_cases

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow

class GetYearFactUseCase(
    private val repository: FactRepository
) {
    operator fun invoke(year: Int): Flow<Resource<Fact>> {
        return repository.getYearFact(year)
    }
}