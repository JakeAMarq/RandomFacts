package com.jakem.randomfacts.feature_facts.data.repository

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.data.remote.FactApi
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.model.FactType
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

// TODO: Implement local backup of facts
class FactRepositoryImpl(
    private val api: FactApi
): FactRepository {

    override fun getNumberFacts(start: Int, end: Int): Flow<Resource<List<Fact>>> = flow {
        emit(Resource.Loading())

        try {
            val factMapResponse = api.getFactForNumbers(start, end)

            if (factMapResponse.isSuccessful && factMapResponse.body() != null) {

                val factList = factMapResponse.body()!!.map { entry ->
                    Fact(
                        number = entry.key,
                        text = entry.value,
                        type = FactType.Number
                    )
                }

                emit(Resource.Success(factList))
            } else {
                emit(Resource.Error("Oops, something went wrong!"))
            }
        } catch(e: HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection."))
        }
    }

    override fun getYearFacts(startYear: Int, endYear: Int): Flow<Resource<List<Fact>>> = flow {
        emit(Resource.Loading())

        try {
            val factMapResponse = api.getFactForYears(startYear, endYear)

            if (factMapResponse.isSuccessful) {

                val factList = factMapResponse.body()!!.map { entry ->
                    Fact(
                        number = entry.key,
                        text = entry.value,
                        type = FactType.Year
                    )
                }

                emit(Resource.Success(factList))
            } else {
                emit(Resource.Error("Oops, something went wrong!"))
            }
        } catch(e: HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection."))
        }
    }
}