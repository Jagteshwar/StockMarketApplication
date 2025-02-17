package com.jagteshwar.stockmarketapplication.data.repository

import com.jagteshwar.stockmarketapplication.data.csv.CSVParser
import com.jagteshwar.stockmarketapplication.data.local.StockDatabase
import com.jagteshwar.stockmarketapplication.data.mapper.toCompanyListing
import com.jagteshwar.stockmarketapplication.data.mapper.toCompanyListingEntity
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
    db: StockDatabase,
    private val companyListingParse: CSVParser<CompanyListing>
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
                companyListingParse.parse(response.byteStream())
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data."))
                null
            }
            catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data."))
                null
            }

            remoteListings?.let {listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListing(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}