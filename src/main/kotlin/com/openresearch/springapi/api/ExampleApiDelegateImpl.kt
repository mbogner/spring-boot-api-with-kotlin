package com.openresearch.springapi.api

import com.openresearch.springapi.api.mapper.dozer.ExampleDozerMapper
import com.openresearch.springapi.api.mapper.mapstruct.ExampleMapStructMapper
import com.openresearch.springapi.logging.LoggedMethod
import com.openresearch.springapi.model.ExampleDto
import com.openresearch.springapi.model.ExampleListDto
import com.openresearch.springapi.model.ExampleNewDto
import com.openresearch.springapi.service.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID
import javax.transaction.Transactional

/**
 * This class is using MapStruct and Dozer for demo purpose.
 */
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
        val created = exampleService.createExample(exampleMapStructMapper.mapNewDtoToEntity(requestBody))
        val dto = exampleMapStructMapper.mapEntityToNewDto(created)
        return ResponseEntity.status(HttpStatus.CREATED).body(dto)
    }

    @LoggedMethod
    override fun readExamples(xAPIKey: UUID?): ResponseEntity<ExampleListDto> {
        val examples = exampleService.readAllExamples()
        val dtos = exampleDozerMapper.mapToList(examples, ExampleDto::class.java)
        return ResponseEntity.ok(ExampleListDto().content(dtos))
    }

}
