package com.openresearch.springapi.api

import com.openresearch.springapi.api.mapper.dozer.ExampleDozerMapper
import com.openresearch.springapi.api.mapper.mapstruct.ExampleMapStructMapper
import com.openresearch.springapi.logging.LoggedMethod
import com.openresearch.springapi.model.Example
import com.openresearch.springapi.model.ExampleDto
import com.openresearch.springapi.model.ExampleNewDto
import com.openresearch.springapi.service.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID
import javax.transaction.Transactional

@Component
class ExampleApiDelegateImpl @Autowired constructor(
    private val exampleService: ExampleService,
    private val exampleDozerMapper: ExampleDozerMapper,
    private val exampleMapStructMapper: ExampleMapStructMapper,
) : ExampleApiDelegate {

    @LoggedMethod
    @Transactional
    override fun createExample(
        xApiKey: UUID,
        requestBody: ExampleNewDto
    ): ResponseEntity<ExampleDto> {
        val dto = withMapStruct(requestBody)
        return ResponseEntity.status(HttpStatus.CREATED).body(dto)
    }

    private fun withDozer(requestBody: ExampleNewDto): ExampleDto {
        val created = exampleService.createExample(exampleDozerMapper.mapTo(requestBody, Example::class.java))
        return exampleDozerMapper.mapTo(created, ExampleDto::class.java)
    }

    private fun withMapStruct(requestBody: ExampleNewDto): ExampleDto {
        val created = exampleService.createExample(exampleMapStructMapper.mapNewDtoToEntity(requestBody))
        return exampleMapStructMapper.mapEntityToNewDto(created)
    }

}
