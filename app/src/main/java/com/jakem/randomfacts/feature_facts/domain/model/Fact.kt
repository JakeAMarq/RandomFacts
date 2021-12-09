package com.jakem.randomfacts.feature_facts.domain.model

import com.jakem.randomfacts.feature_facts.data.local.entity.FactEntity

data class Fact(
    val number: Int,
    val text: String,
    val type: FactType
) {
    fun toFactEntity(): FactEntity {
        return FactEntity(
            number = number,
            text = text
        )
    }
}