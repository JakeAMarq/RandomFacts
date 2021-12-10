package com.jakem.randomfacts.feature_facts.data.repository

import com.jakem.randomfacts.core.util.Resource
import com.jakem.randomfacts.feature_facts.data.local.FactDao
import com.jakem.randomfacts.feature_facts.data.remote.FactApi
import com.jakem.randomfacts.feature_facts.domain.model.Fact
import com.jakem.randomfacts.feature_facts.domain.repository.FactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FactRepositoryImpl(
    private val api: FactApi,
    private val dao: FactDao
): FactRepository {

    override fun getNumberFacts(start: Int, end: Int): Flow<Resource<List<Fact>>> = flow {
        emit(Resource.Loading())

        // Emit local backup as we attempt to pull new facts from server
        val localFactList = dao.getFacts(start, end)
        emit(Resource.Loading(data = localFactList))

        try {
            val factMapResponse = api.getFactForNumbers(start, end)

            if (factMapResponse.isSuccessful && factMapResponse.body() != null) {

                val factList = factMapResponse.body()!!.map { entry ->
                    Fact(
                        number = entry.key,
                        text = entry.value
                    )
                }

                // Update local backup
                dao.deleteFacts(start, end)
                dao.insertFacts(factList)
            } else {
                emit(Resource.Error(
                    message = "Oops, something went wrong!",
                    data = localFactList
                ))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = localFactList
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = localFactList
            ))
        }

        val newFactList = dao.getFacts(start, end)
        emit(Resource.Success(data = newFactList))
    }

    override fun getYearFacts(startYear: Int, endYear: Int): Flow<Resource<List<Fact>>> = flow {
        emit(Resource.Loading())

        try {
            val factMapResponse = api.getFactForYears(startYear, endYear)

            if (factMapResponse.isSuccessful) {

                val factList = factMapResponse.body()!!.map { entry ->
                    Fact(
                        number = entry.key,
                        text = entry.value
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