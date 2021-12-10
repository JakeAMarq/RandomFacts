package com.jakem.randomfacts.feature_facts.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fact(
    @PrimaryKey
    val number: Int,
    val text: String
)