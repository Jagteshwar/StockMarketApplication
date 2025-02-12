package com.jagteshwar.stockmarketapplication.data.mapper

import com.jagteshwar.stockmarketapplication.data.local.CompanyListingEntity
import com.jagteshwar.stockmarketapplication.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}