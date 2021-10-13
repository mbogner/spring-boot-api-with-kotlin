package com.openresearch.springapi.model.base

import java.io.Serializable

interface Identifiable<T : Serializable> {

    fun getIdentifier(): T?

}
