package com.jakem.randomfacts.feature_facts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jakem.randomfacts.feature_facts.domain.model.Fact

@Database(
    entities = [Fact::class],
    version = 1
)
abstract class FactDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "fact_db"
    }

    abstract val dao: FactDao
}