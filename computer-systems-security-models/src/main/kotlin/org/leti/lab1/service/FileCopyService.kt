package org.leti.lab1.service

import org.leti.lab1.component.showErrorPopup
import org.leti.lab1.component.showPopup
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class FileCopyService {

    fun copyFile(pathToSourceFile: String, pathToTargetFile: String) {
        try {
            val sourceFile = Paths.get(pathToSourceFile)
            if (!Files.exists(sourceFile)) {
                showPopup("Исходный файл $pathToSourceFile не существует.")
                return
            }
            copyFile(sourceFile, pathToTargetFile)
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
    }

    fun copyFileWithoutNotification(sourceFile: Path, pathToTargetFile: String) {
        doCopyFile(sourceFile, pathToTargetFile, false)
    }

    fun copyFile(sourceFile: Path, pathToTargetFile: String) {
        doCopyFile(sourceFile, pathToTargetFile, true)
    }

    private fun doCopyFile(sourceFile: Path, pathToTargetFile: String, withNotification: Boolean) {
        try {
            val targetFile = Paths.get(pathToTargetFile)
            Files.copy(sourceFile, targetFile)
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
        if (withNotification) {
            showPopup("Копирование прошло успешно")
        }
    }
}