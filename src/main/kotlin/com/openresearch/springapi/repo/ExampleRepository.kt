package com.openresearch.springapi.repo

import com.openresearch.springapi.model.Example
import com.openresearch.springapi.repo.base.ExtendedJpaRepository
import java.util.UUID

interface ExampleRepository : ExtendedJpaRepository<UUID, Example>
