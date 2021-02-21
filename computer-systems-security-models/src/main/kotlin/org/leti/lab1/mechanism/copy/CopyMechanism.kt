package org.leti.lab1.mechanism.copy

import java.nio.file.Path

@FunctionalInterface
interface CopyMechanism {

    fun copy(sourceFile: Path, targetFile: Path)
}