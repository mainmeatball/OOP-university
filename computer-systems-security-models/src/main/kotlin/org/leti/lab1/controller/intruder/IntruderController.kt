package org.leti.lab1.controller.intruder

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TreeView
import org.leti.lab1.config.INTRUDER_DIR
import org.leti.lab1.config.PUBLIC_DIR
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.DirectoryPollingService
import org.leti.lab1.service.FileCopyService
import java.io.File

class IntruderController {

    @FXML
    lateinit var sourceDirectoryViewer: TreeView<String>

    @FXML
    lateinit var intruderDirectoryViewer: TreeView<String>

    @FXML
    lateinit var copyValuableObjectButton: Button

    private val directoryInitializationService = DirectoryInitializationService()

    private val fileCopyService = FileCopyService()

    private val directoryPollingService = DirectoryPollingService(PUBLIC_DIR)

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        val absoluteSourcePath = PUBLIC_DIR + File.separator + sourceDirectoryViewer.selectionModel.selectedItem.value
        val absoluteTargetPath = INTRUDER_DIR + File.separator + sourceDirectoryViewer.selectionModel.selectedItem.value
        fileCopyService.copyFile(absoluteSourcePath, absoluteTargetPath)
        reloadIntruderDirectory()
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectories()
        directoryPollingService.startPolling {
            Platform.runLater { reloadSourceDirectory() }
        }
    }

    private fun reloadDirectories() {
        reloadSourceDirectory()
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