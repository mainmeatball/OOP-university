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
import javafx.stage.Stage
import org.leti.lab1.component.showPopup
import org.leti.lab1.config.PRIVATE_DIR
import org.leti.lab1.config.PUBLIC_DIR
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileCopyService
import java.io.File

private const val MAIN_MENU = "user/main-menu.fxml"

class ValuableObjectCopyController {

    @FXML
    lateinit var directoryViewer: TreeView<String>

    @FXML
    lateinit var sourceFileName: TextField

    @FXML
    lateinit var targetDirectoryName: TextField

    @FXML
    lateinit var targetFileName: TextField

    @FXML
    lateinit var backToMenuButton: Button

    @FXML
    lateinit var copyValuableObjectButton: Button

    private val directoryInitializationService = DirectoryInitializationService()

    private val fileCopyService = FileCopyService()

    @FXML
    private fun switchBackToMenu(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(MAIN_MENU))
        backToMenuButton.scene.root = newScene
        event.consume()
    }

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        fileCopyService.copyFile(sourceFileName.text, targetDirectoryName.text + File.separator + targetFileName.text)
        reloadDirectoryTree()
        event.consume()
    }

    @FXML
    private fun chooseSourceFile(event: ActionEvent) {
        val fileChooser = FileChooser()
        fileChooser.initialDirectory = File(PRIVATE_DIR)
        val selectedFile = fileChooser.showOpenDialog(sourceFileName.scene.window)
        selectedFile?.let { sourceFileName.text = it.absolutePath }
        event.consume()
    }

    @FXML
    private fun chooseTargetDirectory(event: ActionEvent) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.initialDirectory = File(PUBLIC_DIR)
        val selectedDirectory = directoryChooser.showDialog(targetDirectoryName.scene.window)
        selectedDirectory?.let { targetDirectoryName.text = it.absolutePath }
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectoryTree()
    }

    private fun reloadDirectoryTree(path: String = PUBLIC_DIR) {
        directoryInitializationService.initialize(directoryViewer, path)
    }
}