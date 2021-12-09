package com.jakem.randomfacts.feature_facts.domain.model

data class Fact(
    val number: Int,
    val text: String,
    val type: FactType
)