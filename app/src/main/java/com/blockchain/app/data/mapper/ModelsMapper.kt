package com.blockchain.app.data.mapper

interface ModelsMapper<DTOModel, DomainModel> : DomainMapper<DomainModel, DTOModel>,
    DTOMapper<DTOModel, DomainModel>

interface DTOMapper<DTOModel, DomainModel> {

    fun mapToDTOModel(domainModel: DomainModel): DTOModel

    fun mapToDTOModelList(domainModelList: List<DomainModel>): List<DTOModel>
}

interface DomainMapper<DomainModel, DTOModel> {

    fun mapToDomainModel(dtoModel: DTOModel): DomainModel

    fun mapToDomainModelList(dtoModelList: List<DTOModel>): List<DomainModel>
}
