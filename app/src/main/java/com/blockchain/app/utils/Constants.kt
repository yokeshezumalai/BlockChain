package com.blockchain.app.utils


class Constants {
    companion object{
        const val BASE_API_URL : String = "https://api.blockchain.info"
        const val CHARTS_URL = "$BASE_API_URL/charts/market-price?format=json&timespan=30days"
    }
}