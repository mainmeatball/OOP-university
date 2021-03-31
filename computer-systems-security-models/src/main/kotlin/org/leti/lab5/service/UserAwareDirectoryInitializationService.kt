package org.leti.lab5.service

import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.User

class UserAwareDirectoryInitializationService : DirectoryInitializationService() {

    lateinit var currentUser: User

    override fun allowDirectory(dir: TypeAwareTreeItem): Boolean {
        if (!::currentUser.isInitialized) {
            // allow all directories if no user is set
            return true
        }
        val securityFolderType = dir.security
        val roles = InMemoryStorage.roleSet.associateBy { it.name }
        return currentUser.properties.entries.asSequence()
            .filter { it.value }
            .map { roles[it.key] }
            .filterNotNull()
            .flatMap { it.properties.entries }
            .filter { it.value }
            .map { it.key }
            .map { InMemoryStorage.securityTypeSet.find { item -> item.name == it } }
            .any { it == securityFolderType }
    }

    override fun getAvailableSecurityTypes() = InMemoryStorage.securityTypeSet.map { it.name }
}