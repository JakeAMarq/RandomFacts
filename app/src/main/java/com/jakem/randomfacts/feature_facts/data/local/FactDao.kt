package com.jakem.randomfacts.feature_facts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jakem.randomfacts.feature_facts.data.local.entity.FactEntity

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacts(facts: List<FactEntity>)

    @Query("DELETE FROM FactEntity WHERE number BETWEEN :start AND :end")
    suspend fun deleteFacts(start: Int, end: Int = start)

    @Query("SELECT * FROM FactEntity WHERE number BETWEEN :start AND :end")
    suspend fun getFacts(start: Int, end: Int = start): List<FactEntity>
}