package org.leti.lab1.controller.user

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TreeView
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab1.config.PRIVATE_DIR
import org.leti.lab1.config.PUBLIC_DIR
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileService
import java.io.File

private const val MAIN_MENU = "user/main-menu.fxml"

class ValuableObjectCopyController {

    @FXML
    lateinit var sourceDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var targetDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var targetFileName: TextField

    @FXML
    lateinit var backToMenuButton: Button

    @FXML
    lateinit var copyValuableObjectButton: Button

    private val directoryInitializationService = DirectoryInitializationService()

    private val fileService = FileService()

    @FXML
    fun initialize() {
        reloadDirectoryTree()
    }

    @FXML
    private fun switchBackToMenu(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(MAIN_MENU))
        backToMenuButton.scene.root = newScene
        event.consume()
    }

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        val sourceFileName = sourceDirectoryViewer.selectionModel.selectedItem.value
        val sourceAbsoluteFilePath = PRIVATE_DIR + File.separator + sourceFileName
        val pathToTargetFile = targetDirectoryViewer.currentDirectory + File.separator + targetFileName.text
        fileService.copyWithNotification(sourceAbsoluteFilePath, pathToTargetFile)
        reloadDirectoryTree()
        event.consume()
    }

    private fun reloadDirectoryTree() {
        directoryInitializationService.initialize(sourceDirectoryViewer, PRIVATE_DIR)
        directoryInitializationService.initialize(targetDirectoryViewer, PUBLIC_DIR)
    }
}