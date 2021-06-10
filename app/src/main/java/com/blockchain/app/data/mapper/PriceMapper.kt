package com.blockchain.app.data.mapper

import com.blockchain.app.data.model.MarketValue
import com.blockchain.app.data.model.SingleEntityData

class PriceMapper : DomainMapper<MarketValue, SingleEntityData> {

    override fun mapToDomainModel(dtoModel: SingleEntityData): MarketValue {
        return MarketValue(
            dateMillis = dtoModel.x,
            price = dtoModel.y
        )
    }

    override fun mapToDomainModelList(dtoModelList: List<SingleEntityData>?): List<MarketValue>? {
        return dtoModelList?.map { mapToDomainModel(it) }
    }
}