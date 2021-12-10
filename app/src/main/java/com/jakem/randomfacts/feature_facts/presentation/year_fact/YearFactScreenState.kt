package com.jakem.randomfacts.feature_facts.presentation.year_fact

import com.jakem.randomfacts.feature_facts.domain.model.Fact

data class YearFactScreenState(
    val fact: Fact? = null,
    val isLoading: Boolean = false
)
