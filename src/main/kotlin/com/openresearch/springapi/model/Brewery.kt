package com.openresearch.springapi.model

import com.openresearch.springapi.model.base.AbstractVersionedMutableEntity
import com.openresearch.springapi.model.base.AssignableUUIDGenerator
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "breweries")
class Brewery : AbstractVersionedMutableEntity<UUID>() {

    @Id
    @GeneratedValue(generator = AssignableUUIDGenerator.NAME)
    @GenericGenerator(name = AssignableUUIDGenerator.NAME, strategy = AssignableUUIDGenerator.PATH)
    var id: UUID? = null

    @NotBlank
    var name: String? = null

    override fun getIdentifier(): UUID? {
        return id
    }

}
