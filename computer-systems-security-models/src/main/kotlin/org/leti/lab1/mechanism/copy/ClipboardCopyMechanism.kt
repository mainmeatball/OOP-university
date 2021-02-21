package org.leti.lab1.mechanism.copy

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import java.nio.file.Files
import java.nio.file.Path

class ClipboardCopyMechanism : CopyMechanism {

    override fun copy(sourceFile: Path, targetFile: Path) {
        val sourceContent = Files.readAllLines(sourceFile).joinToString(separator = "\n")
        val clipboardContent = ClipboardContent()
        clipboardContent.putString(sourceContent)
        Clipboard.getSystemClipboard().setContent(clipboardContent)
        Files.writeString(targetFile, sourceContent)
    }
}