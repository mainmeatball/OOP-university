package org.leti.lab5.dao

import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.Role
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.User

class RoleDao {

    fun findAll() = InMemoryStorage.roleSet

    fun save(vararg roles: Role) {
        InMemoryStorage.roleSet += roles
    }

    fun remove(vararg roles: Role) {
        InMemoryStorage.roleSet.removeAll(roles)
    }
}