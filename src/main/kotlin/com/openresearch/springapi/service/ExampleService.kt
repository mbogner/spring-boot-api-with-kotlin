package com.openresearch.springapi.service

import com.openresearch.springapi.model.Example
import com.openresearch.springapi.repo.ExampleRepository
import com.openresearch.springapi.repo.specification.ExampleByNameContainingSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ExampleService @Autowired constructor(
    private val repository: ExampleRepository,
) {

    @Transactional(Transactional.TxType.MANDATORY)
    fun createExample(example: Example): Example {
        val created = repository.saveAndFlush(example)

        repository.findAll(ExampleByNameContainingSpecification("test"))

        repository.refresh(created) // we do changes in db via trigger
        return created
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    fun readAllExamples(): List<Example> {
        return repository.findAll()
    }

}
