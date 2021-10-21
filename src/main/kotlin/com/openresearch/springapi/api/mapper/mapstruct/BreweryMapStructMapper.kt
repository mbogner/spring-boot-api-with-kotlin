package com.openresearch.springapi.api.mapper.mapstruct

import com.openresearch.springapi.model.Brewery
import com.openresearch.springapi.model.BreweryDto
import com.openresearch.springapi.model.BreweryNewDto
import org.mapstruct.Mapper

@Mapper
interface BreweryMapStructMapper {

    fun mapNewDtoToEntity(dto: BreweryNewDto): Brewery
    fun mapEntityToNewDto(entity: Brewery): BreweryDto
    fun mapEntityToNewDto(entity: List<Brewery>): List<BreweryDto>

}
