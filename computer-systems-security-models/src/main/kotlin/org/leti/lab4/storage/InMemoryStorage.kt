package org.leti.lab4.storage

import org.leti.lab5.component.Role
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.User

object InMemoryStorage {
    val securityTypeSet = mutableSetOf<SecurityType>()
    val userSet = mutableSetOf<User>()
    val roleSet = mutableSetOf<Role>()

    val dirSecurityTypeCache = mutableMapOf<String, SecurityType>()

    fun hasFolder(dir: String) = dirSecurityTypeCache.containsKey(dir)
    fun getFolderSecurityType(dir: String): SecurityType = dirSecurityTypeCache[dir]!!
    fun getSafeFolderSecurityType(dir: String): SecurityType = dirSecurityTypeCache[dir] ?: SecurityType.NON_SECRET
    fun setFolderSecurityType(dir: String, securityType: SecurityType) {
        dirSecurityTypeCache[dir] = securityType
    }
}