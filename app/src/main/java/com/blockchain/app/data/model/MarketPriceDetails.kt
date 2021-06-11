package com.blockchain.app.data.model


class MarketPriceDetails(
    val name: String?,
    val description: String?,
    val unit: String?,
    val bitcoinValues: List<MarketValue>?
)