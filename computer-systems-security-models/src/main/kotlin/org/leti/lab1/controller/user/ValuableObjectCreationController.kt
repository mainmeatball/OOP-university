package org.leti.lab1.controller.user

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.*
import org.leti.lab1.config.PRIVATE_DIR
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileService
import java.io.File

private const val MAIN_MENU = "user/main-menu.fxml"

class ValuableObjectCreationController {

    @FXML
    lateinit var directoryViewer: TreeView<String>

    @FXML
    lateinit var fileName: TextField

    @FXML
    lateinit var textArea: TextArea

    @FXML
    lateinit var backToMenuButton: Button

    @FXML
    lateinit var createValuableObjectButton: Button

    private val fileService = FileService()

    private val directoryInitializationService = DirectoryInitializationService()

    @FXML
    private fun switchBackToMenu(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(MAIN_MENU))
        backToMenuButton.scene.root = newScene
        event.consume()
    }

    @FXML
    private fun createValuableObject(event: ActionEvent) {
        val privateDir = PRIVATE_DIR
        fileService.createFile(privateDir + File.separator + fileName.text, textArea.text)
        reloadDirectoryTree(privateDir)
        clearFields(fileName, textArea)
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectoryTree(PRIVATE_DIR)
    }

    private fun reloadDirectoryTree(path: String) {
        directoryInitializationService.initialize(directoryViewer, path)
    }

    private fun clearFields(vararg textFields: TextInputControl) {
        textFields.forEach { it.clear() }
    }
}