package org.leti.lab5.dao

import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.User

class UserDao {

    fun findAll() = InMemoryStorage.userSet

    fun save(vararg users: User) {
        InMemoryStorage.userSet += users
    }

    fun remove(vararg users: User) {
        InMemoryStorage.userSet.removeAll(users)
    }
}