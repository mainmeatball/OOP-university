package org.leti.lab1.service

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import org.leti.lab1.component.showErrorPopup
import org.leti.lab1.component.showPopup
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class FileService {

    fun createFile(pathToFile: String, content: String) {
        try {
            val file = Paths.get(pathToFile)
            if (!Files.exists(file)) {
                Files.createFile(file)
            }
            Files.writeString(file, content)
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
        showPopup("Файл $pathToFile успешно создан.")
    }

    fun copyThroughClipboard(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, false, ::doCopyThroughClipboard)
    }

    fun copyThroughClipboard(sourceFile: Path, pathToTargetFile: String) {
        doCopy(pathToTargetFile, false) { targetFile ->
            doCopyThroughClipboard(sourceFile, targetFile)
        }
    }

    fun copyThroughClipboardWithNotification(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, true, ::doCopyThroughClipboard)
    }

    fun copyThroughClipboardWithNotification(sourceFile: Path, pathToTargetFile: String) {
        doCopy(pathToTargetFile, true) { targetFile ->
            doCopyThroughClipboard(sourceFile, targetFile)
        }
    }

    private fun doCopyThroughClipboard(sourceFile: Path, targetFile: Path) {
        val sourceContent = Files.readAllLines(sourceFile).joinToString(separator = "\n")
        val clipboardContent = ClipboardContent()
        clipboardContent.putString(sourceContent)
        Clipboard.getSystemClipboard().setContent(clipboardContent)
        Files.writeString(targetFile, sourceContent)
    }

    fun copy(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, false, Files::copy)
    }

    fun copy(sourceFile: Path, pathToTargetFile: String) {
        doCopy(pathToTargetFile, false) { targetFile ->
            Files.copy(sourceFile, targetFile)
        }
    }

    fun copyWithNotification(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, true, Files::copy)
    }

    fun copyWithNotification(sourceFile: Path, pathToTargetFile: String) {
        doCopy(pathToTargetFile, true) { targetFile ->
            Files.copy(sourceFile, targetFile)
        }
    }

    private fun doCopy(pathToSourceFile: String, pathToTargetFile: String, withNotification: Boolean, copy: (Path, Path) -> Unit) {
        try {
            val sourceFile = Paths.get(pathToSourceFile)
            if (!Files.exists(sourceFile)) {
                showPopup("Исходный файл $pathToSourceFile не существует.")
                return
            }
            doCopy(pathToTargetFile, withNotification) { targetFile ->
                copy(sourceFile, targetFile)
            }
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
    }

    private fun doCopy(pathToTargetFile: String, withNotification: Boolean, copy: (Path) -> Unit) {
        val targetFile = Paths.get(pathToTargetFile)
        copy(targetFile)
        if (withNotification) {
            showPopup("Копирование прошло успешно")
        }
    }
}