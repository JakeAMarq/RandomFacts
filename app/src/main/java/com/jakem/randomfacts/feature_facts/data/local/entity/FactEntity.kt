package com.jakem.randomfacts.feature_facts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.model.FactType

@Entity
data class FactEntity(
    @PrimaryKey
    val number: Int,
    val text: String,
) {
    fun toFact(): Fact {
        return Fact(
            number = number,
            text = text,
            type = FactType.Number
        )
    }
}
