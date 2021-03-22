package org.leti.lab4.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.leti.lab4.component.SecurityFolderType
import java.io.File


class ApplicationStateService {

    private val mapper = ObjectMapper()

    fun saveState(stateMap: Map<String, SecurityFolderType>) {
        val stateFile = getStateFile() ?: return
        stateFile.createNewFile()
        mapper.writerWithDefaultPrettyPrinter().writeValue(stateFile, stateMap)
    }

    fun fetchState(): Map<String, SecurityFolderType> {
        val stateFile = getStateFile() ?: return mapOf()
        val stateMap = mapper.readValue(stateFile, Map::class.java)
        return stateMap.entries
            .associate { it.key as String to SecurityFolderType.valueOf(it.value as String) }
    }

    private fun getStateFile(): File? {
        val stateFolder = File(JAR_APP_STATE_FOLDER)
        if (stateFolder.exists()) {
            val stateFile = File(JAR_APP_STATE_FILE)
            return if (stateFile.exists()) {
                stateFile
            } else {
                null
            }
        }
        val stateFile = File(STATE_FILE)
        if (!stateFile.exists()) {
            return null
        }
        return stateFile
    }

    companion object {
        private const val JAR_APP_STATE_FOLDER = "./state"
        private const val JAR_APP_STATE_FILE = "./state/state.json"
        private const val STATE_FILE = "./computer-systems-security-models/state/state.json"
    }
}