package com.openresearch.springapi.service

import com.openresearch.springapi.model.Brewery
import com.openresearch.springapi.repo.BreweryRepository
import com.openresearch.springapi.repo.specification.BreweriesByNameContainingSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BreweryService @Autowired constructor(
    private val repository: BreweryRepository,
) {

    @Transactional(Transactional.TxType.MANDATORY)
    fun createBrewery(brewery: Brewery): Brewery {
        val created = repository.saveAndFlush(brewery)
        repository.refresh(created) // triggers are used, so we have to reload it
        return created
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    fun readAllBreweries(): List<Brewery> {
        // this just showcases how to use specification queries
        repository.findAll(
            BreweriesByNameContainingSpecification(
                "test"
            )
        )

        return repository.findAll()
    }

}
