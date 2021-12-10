package com.jakem.randomfacts.feature_facts.data.repository

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFactRepository : FactRepository {

    companion object {
        private val FACTS = List(500) {
            Fact(
                number = it,
                text = "$it is definitely a number!"
            )
        }

        private val YEAR_FACTS = List(2022) {
            Fact(
                number = it,
                text = "$it was definitely a year in which things happened!"
            )
        }
    }


    override fun getNumberFacts(start: Int, end: Int): Flow<Resource<List<Fact>>> {
        return flow { emit(Resource.Success(FACTS.subList(start, end + 1))) }
    }

    override fun getYearFact(year: Int): Flow<Resource<Fact>> {
        return flow { emit(Resource.Success(YEAR_FACTS[year])) }
    }
}