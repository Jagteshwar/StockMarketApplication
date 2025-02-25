package com.jagteshwar.stockmarketapplication.presentation.company_listing

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}