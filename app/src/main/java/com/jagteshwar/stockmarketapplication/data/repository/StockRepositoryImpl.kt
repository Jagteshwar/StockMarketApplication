package com.jagteshwar.stockmarketapplication.data.repository

import com.jagteshwar.stockmarketapplication.data.local.StockDatabase
import com.jagteshwar.stockmarketapplication.data.mapper.toCompanyListing
import com.jagteshwar.stockmarketapplication.data.remote.StockApi
import com.jagteshwar.stockmarketapplication.domain.model.CompanyListing
import com.jagteshwar.stockmarketapplication.domain.repository.StockRepository
import com.jagteshwar.stockmarketapplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try{
                val response = api.getListings()
                response.byteStream()
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data."))
            }
            catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data."))
            }
        }
    }
}