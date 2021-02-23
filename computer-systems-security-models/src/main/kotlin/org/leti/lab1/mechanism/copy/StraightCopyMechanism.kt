package org.leti.lab1.mechanism.copy

import java.nio.file.Files
import java.nio.file.Path

class StraightCopyMechanism : CopyMechanism {

    override fun copy(sourceFile: Path, targetFile: Path) {
        Files.copy(sourceFile, targetFile)
    }
}