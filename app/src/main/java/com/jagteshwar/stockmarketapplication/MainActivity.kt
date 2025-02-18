package com.jagteshwar.stockmarketapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jagteshwar.stockmarketapplication.presentation.company_listing.NavGraph
import com.jagteshwar.stockmarketapplication.presentation.company_listing.NavGraphs
import com.jagteshwar.stockmarketapplication.ui.theme.StockMarketApplicationTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockMarketApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                        DestinationsNavHost(navGraph = NavGraphs.root)
                }

            }
        }
    }
}

