package com.openresearch.springapi.api.mapper.mapstruct

import com.openresearch.springapi.model.Example
import com.openresearch.springapi.model.ExampleDto
import com.openresearch.springapi.model.ExampleNewDto
import org.mapstruct.Mapper

@Mapper
interface ExampleMapStructMapper {

    fun mapNewDtoToEntity(dto: ExampleNewDto): Example
    fun mapEntityToNewDto(entity: Example): ExampleDto
    fun mapEntityToNewDto(entity: List<Example>): List<ExampleDto>

}
