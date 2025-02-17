package com.jagteshwar.stockmarketapplication.domain.repository

import com.jagteshwar.stockmarketapplication.domain.model.CompanyListing
import com.jagteshwar.stockmarketapplication.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

suspend fun getCompanyListing(
    fetchFromRemote: Boolean,
    query: String
): Flow<Resource<List<CompanyListing>>>
}