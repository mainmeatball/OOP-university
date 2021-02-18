package org.leti.lab1.service

import org.leti.lab1.component.showErrorPopup
import org.leti.lab1.component.showPopup
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


class FileCopyService {

    fun copyFile(pathToSourceFile: String, pathToTargetFile: String) {
        try {
            val sourceFile = Paths.get(pathToSourceFile)
            if (!Files.exists(sourceFile)) {
                showPopup("Исходный файл $pathToSourceFile не существует.")
                return
            }
            val targetFile = Paths.get(pathToTargetFile)
            Files.copy(sourceFile, targetFile)
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
        showPopup("Копирование прошло успешно")
    }
}