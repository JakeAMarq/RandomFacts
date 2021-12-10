package com.jakem.randomfacts.feature_facts.data.repository

import com.jakem.randomfacts.R
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
            val factMapResponse = api.getNumberFacts(start, end)

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
                    messageId = R.string.error_something_went_wrong,
                    data = localFactList
                ))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(
                messageId = R.string.error_something_went_wrong,
                data = localFactList
            ))
        } catch(e: IOException) {
            emit(Resource.Error(
                messageId = R.string.error_no_internet,
                data = localFactList
            ))
        }

        val newFactList = dao.getFacts(start, end)
        emit(Resource.Success(data = newFactList))
    }

    override fun getYearFact(year: Int): Flow<Resource<Fact>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getYearFact(year)

            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(R.string.error_something_went_wrong))
            }

        } catch(e: HttpException) {
            emit(Resource.Error(R.string.error_something_went_wrong))
        } catch(e: IOException) {
            emit(Resource.Error(R.string.error_no_internet))
        }
    }
}