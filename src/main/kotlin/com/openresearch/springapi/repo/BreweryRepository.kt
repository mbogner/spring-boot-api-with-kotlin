package com.openresearch.springapi.repo

import com.openresearch.springapi.model.Brewery
import com.openresearch.springapi.repo.base.ExtendedJpaRepository
import java.util.UUID

interface BreweryRepository : ExtendedJpaRepository<UUID, Brewery>
