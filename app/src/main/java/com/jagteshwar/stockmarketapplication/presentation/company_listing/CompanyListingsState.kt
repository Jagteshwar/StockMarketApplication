package com.jagteshwar.stockmarketapplication.presentation.company_listing

import com.jagteshwar.stockmarketapplication.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
