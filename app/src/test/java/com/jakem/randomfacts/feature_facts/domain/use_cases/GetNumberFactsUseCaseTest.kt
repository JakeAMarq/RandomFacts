package com.jakem.randomfacts.feature_facts.domain.use_cases

import com.jakem.randomfacts.feature_facts.data.repository.FakeFactRepository
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetNumberFactsUseCaseTest {

    private lateinit var fakeRepository: FactRepository
    private lateinit var getNumberFacts: GetNumberFactsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeFactRepository()
        getNumberFacts = GetNumberFactsUseCase(fakeRepository)
    }

    @Test
    fun `Start greater than end throws IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            getNumberFacts(1, 0)
        }
    }

    @Test
    fun `Returns facts from start to end inclusive`() = runBlocking {
        val facts = getNumberFacts(1, 50).first()
        val numbers = facts.data?.map { it.number }
        assertEquals((1..50).toList(), numbers)
    }
}