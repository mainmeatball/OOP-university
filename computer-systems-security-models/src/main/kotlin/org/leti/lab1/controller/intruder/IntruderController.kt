package org.leti.lab1.controller.intruder

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TreeView
import org.leti.lab1.config.INTRUDER_DIR
import org.leti.lab1.config.PUBLIC_DIR
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.DirectoryPollingService
import org.leti.lab1.service.FileService
import java.io.File
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.WatchEvent

class IntruderController {

    @FXML
    lateinit var sourceDirectoryViewer: TreeView<String>

    @FXML
    lateinit var intruderDirectoryViewer: TreeView<String>

    @FXML
    lateinit var copyValuableObjectButton: Button

    @FXML
    lateinit var automaticCopyCheckbox: CheckBox

    private val directoryInitializationService = DirectoryInitializationService()

    private val fileService = FileService()

    private val directoryPollingService = DirectoryPollingService(PUBLIC_DIR)

    private val eventKindMap = mapOf(
        ENTRY_CREATE to ::copySourceFile
    )

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        val absoluteSourcePath = PUBLIC_DIR + File.separator + sourceDirectoryViewer.selectionModel.selectedItem.value
        val absoluteTargetPath = INTRUDER_DIR + File.separator + sourceDirectoryViewer.selectionModel.selectedItem.value
        fileService.copyThroughClipboardWithNotification(absoluteSourcePath, absoluteTargetPath)
        reloadIntruderDirectory()
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectories()
        directoryPollingService.startPolling(::reloadSourceDirectory)
    }

    private fun reloadDirectories() {
        reloadSourceDirectory()
        reloadIntruderDirectory()
    }

    private fun reloadSourceDirectory(sourceFile: Path, eventKind: WatchEvent.Kind<out Any>) {
        Platform.runLater {
            reloadSourceDirectory()
            eventKindMap[eventKind]?.invoke(sourceFile)
        }
    }

    private fun copySourceFile(sourceFile: Path) {
        if (!automaticCopyCheckbox.isSelected) {
            return
        }
        val absoluteTargetPath = INTRUDER_DIR + File.separator + sourceFile.fileName
        fileService.copyThroughClipboard(sourceFile, absoluteTargetPath)
        reloadIntruderDirectory()
    }

    private fun reloadSourceDirectory() {
        reloadDirectoryTree(sourceDirectoryViewer, PUBLIC_DIR)
    }

    private fun reloadIntruderDirectory() {
        reloadDirectoryTree(intruderDirectoryViewer, INTRUDER_DIR)
    }

    private fun reloadDirectoryTree(directoryViewer: TreeView<String>, path: String) {
        directoryInitializationService.initialize(directoryViewer, path)
    }
}