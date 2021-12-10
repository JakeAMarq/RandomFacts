package com.jakem.randomfacts.feature_facts.domain.use_cases

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow

class GetNumberFactsUseCase(
    private val repository: FactRepository
) {
    operator fun invoke(start: Int, end: Int = start): Flow<Resource<List<Fact>>> {

        require(start <= end) { "Start cannot be greater than end" }

        return repository.getNumberFacts(start, end)
    }
}