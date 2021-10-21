package com.openresearch.springapi.api

import com.openresearch.springapi.api.mapper.dozer.BreweryDozerMapper
import com.openresearch.springapi.api.mapper.mapstruct.BreweryMapStructMapper
import com.openresearch.springapi.logging.LoggedMethod
import com.openresearch.springapi.model.BreweryDto
import com.openresearch.springapi.model.BreweryListDto
import com.openresearch.springapi.model.BreweryNewDto
import com.openresearch.springapi.service.BreweryService
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
    private val breweryService: BreweryService,
    private val exampleDozerMapper: BreweryDozerMapper,
    private val breweryMapStructMapper: BreweryMapStructMapper,
) : BreweryApiDelegate {

    @LoggedMethod
    @Transactional
    override fun createBrewery(
        xApiKey: UUID,
        requestBody: BreweryNewDto
    ): ResponseEntity<BreweryDto> {
        val created = breweryService.createBrewery(breweryMapStructMapper.mapNewDtoToEntity(requestBody))
        val dto = breweryMapStructMapper.mapEntityToNewDto(created)
        return ResponseEntity.status(HttpStatus.CREATED).body(dto)
    }

    @LoggedMethod
    override fun readBreweries(xAPIKey: UUID?): ResponseEntity<BreweryListDto> {
        val examples = breweryService.readAllBreweries()
        val dtos = exampleDozerMapper.mapToList(examples, BreweryDto::class.java)
        return ResponseEntity.ok(BreweryListDto().content(dtos))
    }

}
