package com.openresearch.springapi

import com.openresearch.springapi.repo.ExampleRepository
import com.openresearch.springapi.repo.base.ExtendedJpaRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [
        ExampleRepository::class,
    ],
    repositoryBaseClass = ExtendedJpaRepositoryImpl::class
)
@SpringBootApplication
class SpringApiApplication

fun main(args: Array<String>) {
    runApplication<SpringApiApplication>(*args)
}
