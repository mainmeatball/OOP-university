package org.leti.lab4.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.leti.lab5.component.FolderColor
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.UserRole
import java.io.File

private const val JAR_APP_STATE_FOLDER = "./state"
private const val JAR_APP_STATE_FILE = "$JAR_APP_STATE_FOLDER/state.json"
private const val STATE_FILE = "./computer-systems-security-models/state/state.json"
private const val JAR_APP_ROLES_FILE = "$JAR_APP_STATE_FOLDER/roles_users.json"
private const val ROLES_FILE = "./computer-systems-security-models/state/roles_users.json"

object ApplicationStateService {

    private val mapper = ObjectMapper()

    fun saveState(stateMap: Map<String, SecurityType>) {
        val stateFile = getStateFile()
        if (!stateFile.exists()) {
            stateFile.createNewFile()
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, stateMap)
    }

    fun saveRolesUsersState(stateMap: UserRole) {
        val stateFile = getRoleStateFile()
        if (!stateFile.exists()) {
            stateFile.createNewFile()
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, stateMap)
    }

    fun fetchState(): Map<String, SecurityType> {
        val stateFile = getStateFile()
        if (!stateFile.exists()) {
            stateFile.createNewFile()
            mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, mapOf<String, SecurityType>())
        }
        val stateMap = mapper.readValue(stateFile, Map::class.java)
        return stateMap.entries
            .associate {
                val value = it.value as LinkedHashMap<*, *>
                it.key as String to SecurityType(value["name"] as String, value["priority"] as Int, FolderColor.valueOf(value["color"] as String))
            }
    }

    fun fetchRolesUsersState(): UserRole {
        val stateFile = getRoleStateFile()
        if (!stateFile.exists()) {
            stateFile.createNewFile()
            mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, UserRole())
        }
        return mapper.readValue(stateFile, UserRole::class.java)
    }

    private fun getStateFile(): File {
        return doGetStateFile(JAR_APP_STATE_FILE, STATE_FILE)
    }

    private fun getRoleStateFile(): File {
        return doGetStateFile(JAR_APP_ROLES_FILE, ROLES_FILE)
    }

    private fun doGetStateFile(jarAppFile: String, ideaFile: String): File {
        val stateFolder = File(JAR_APP_STATE_FOLDER)
        if (stateFolder.exists()) {
            return File(jarAppFile)
        }
        return File(ideaFile)
    }
}