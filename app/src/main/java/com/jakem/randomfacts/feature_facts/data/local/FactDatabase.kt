package com.jakem.randomfacts.feature_facts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jakem.randomfacts.feature_facts.data.local.entity.FactEntity

@Database(
    entities = [FactEntity::class],
    version = 1
)
abstract class FactDatabase: RoomDatabase() {
    abstract val dao: FactDao
}