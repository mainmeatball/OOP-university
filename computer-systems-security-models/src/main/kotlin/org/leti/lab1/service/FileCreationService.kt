package org.leti.lab1.service

import org.leti.lab1.component.showErrorPopup
import org.leti.lab1.component.showPopup
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


class FileCreationService {

    fun createFile(pathToFile: String, content: String) {
        try {
            val file = Paths.get(pathToFile)
            if (!Files.exists(file)) {
                Files.createFile(file)
            }
            Files.write(file, content.toByteArray())
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
        showPopup("Файл $pathToFile успешно создан.")
    }
}