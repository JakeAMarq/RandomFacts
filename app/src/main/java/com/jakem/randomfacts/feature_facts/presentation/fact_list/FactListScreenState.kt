package com.jakem.randomfacts.feature_facts.presentation.fact_list

import com.jakem.randomfacts.feature_facts.domain.model.Fact

data class FactListScreenState(
    val facts: List<Fact> = emptyList(),
    val isLoading: Boolean = false
)
