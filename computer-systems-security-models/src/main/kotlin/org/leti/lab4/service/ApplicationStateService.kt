package org.leti.lab4.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.leti.lab4.component.SecurityFolderType
import org.leti.lab5.component.table.UserRole
import java.io.File

private const val JAR_APP_STATE_FOLDER = "./state"
private const val JAR_APP_STATE_FILE = "$JAR_APP_STATE_FOLDER/state.json"
private const val STATE_FILE = "./computer-systems-security-models/state/state.json"
private const val JAR_APP_ROLES_FILE = "$JAR_APP_STATE_FOLDER/roles_users.json"
private const val ROLES_FILE = "./computer-systems-security-models/state/roles_users.json"

object ApplicationStateService {

    private val mapper = ObjectMapper()

    fun saveState(stateMap: Map<String, SecurityFolderType>) {
        val stateFile = getStateFile()
        stateFile?.createNewFile() ?: return
        mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, stateMap)
    }

    fun saveRolesUsersState(stateMap: UserRole) {
        val stateFile = getRoleStateFile()
        stateFile?.createNewFile() ?: return
        mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, stateMap)
    }

    fun fetchState(): Map<String, SecurityFolderType> {
        val stateFile = getStateFile() ?: return mapOf()
        val stateMap = mapper.readValue(stateFile, Map::class.java)
        return stateMap.entries
            .associate { it.key as String to SecurityFolderType.valueOf(it.value as String) }
    }

    fun fetchRolesUsersState(): UserRole {
        val stateFile = getRoleStateFile() ?: return UserRole()
        return mapper.readValue(stateFile, UserRole::class.java)
    }

    private fun getStateFile(): File? {
        return doGetStateFile(JAR_APP_STATE_FILE, STATE_FILE)
    }

    private fun getRoleStateFile(): File? {
        return doGetStateFile(JAR_APP_ROLES_FILE, ROLES_FILE)
    }

    private fun doGetStateFile(jarAppFile: String, ideaFile: String): File? {
        val stateFolder = File(JAR_APP_STATE_FOLDER)
        if (stateFolder.exists()) {
            val stateFile = File(jarAppFile)
            return if (stateFile.exists()) {
                stateFile
            } else {
                null
            }
        }
        val stateFile = File(ideaFile)
        return stateFile
    }
}