package org.leti.lab1.service

import org.leti.lab1.component.showErrorPopup
import org.leti.lab1.component.showPopup
import org.leti.lab1.mechanism.copy.ClipboardCopyMechanism
import org.leti.lab1.mechanism.copy.CopyMechanism
import org.leti.lab1.mechanism.copy.StraightCopyMechanism
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class FileService {

    private val clipboardCopyMechanism = ClipboardCopyMechanism()

    private val straightCopyMechanism = StraightCopyMechanism()

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
        doCopy(pathToSourceFile, pathToTargetFile, false, clipboardCopyMechanism)
    }

    fun copyThroughClipboard(sourceFile: Path, pathToTargetFile: String) {
        doCopy(sourceFile, pathToTargetFile, false, clipboardCopyMechanism)
    }

    fun copyThroughClipboardWithNotification(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, true, clipboardCopyMechanism)
    }

    fun copyThroughClipboardWithNotification(sourceFile: Path, pathToTargetFile: String) {
        doCopy(sourceFile, pathToTargetFile, true, clipboardCopyMechanism)
    }

    fun copy(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, false, straightCopyMechanism)
    }

    fun copy(sourceFile: Path, pathToTargetFile: String) {
        doCopy(sourceFile, pathToTargetFile, false, straightCopyMechanism)
    }

    fun copyWithNotification(pathToSourceFile: String, pathToTargetFile: String) {
        doCopy(pathToSourceFile, pathToTargetFile, true, straightCopyMechanism)
    }

    fun copyWithNotification(sourceFile: Path, pathToTargetFile: String) {
        doCopy(sourceFile, pathToTargetFile, true, straightCopyMechanism)
    }

    private fun doCopy(pathToSourceFile: String, pathToTargetFile: String, withNotification: Boolean, copyMechanism: CopyMechanism) {
        try {
            val sourceFile = Paths.get(pathToSourceFile)
            if (!Files.exists(sourceFile)) {
                showPopup("Исходный файл $pathToSourceFile не существует.")
                return
            }
            doCopy(sourceFile, pathToTargetFile, withNotification, copyMechanism)
        } catch (e: IOException) {
            showErrorPopup(e)
            return
        }
    }

    private fun doCopy(sourceFile: Path, pathToTargetFile: String, withNotification: Boolean, copyMechanism: CopyMechanism) {
        val targetFile = Paths.get(pathToTargetFile)
        copyMechanism.copy(sourceFile, targetFile)
        if (withNotification) {
            showPopup("Копирование прошло успешно")
        }
    }
}