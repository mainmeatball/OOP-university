package org.leti.lab4.dao

import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.Role
import org.leti.lab5.component.SecurityType

class SecurityTypeDao {

    fun findAll() = InMemoryStorage.securityTypeSet

    fun save(vararg securityTypes: SecurityType) {
        InMemoryStorage.securityTypeSet += securityTypes
    }

    fun remove(vararg securityTypes: SecurityType) {
        InMemoryStorage.securityTypeSet.removeAll(securityTypes)
    }
}