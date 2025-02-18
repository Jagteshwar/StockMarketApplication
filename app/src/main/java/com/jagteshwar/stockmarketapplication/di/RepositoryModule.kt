package com.jagteshwar.stockmarketapplication.di

import com.jagteshwar.stockmarketapplication.data.csv.CSVParser
import com.jagteshwar.stockmarketapplication.data.csv.CompanyListingParser
import com.jagteshwar.stockmarketapplication.data.repository.StockRepositoryImpl
import com.jagteshwar.stockmarketapplication.domain.model.CompanyListing
import com.jagteshwar.stockmarketapplication.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}