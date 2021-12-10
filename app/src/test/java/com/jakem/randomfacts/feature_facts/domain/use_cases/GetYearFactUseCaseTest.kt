package com.jakem.randomfacts.feature_facts.domain.use_cases

import com.jakem.randomfacts.feature_facts.data.repository.FakeFactRepository
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetYearFactUseCaseTest {

    private lateinit var fakeRepository: FactRepository
    private lateinit var getYearFact: GetYearFactUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeFactRepository()
        getYearFact = GetYearFactUseCase(fakeRepository)
    }

    @Test
    fun `Returns fact for correct year`() = runBlocking {
        val fact = getYearFact(1998).first()
        assertEquals(1998, fact.data?.number)
    }
}