package org.leti.lab1.controller.intruder

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TreeView
import org.leti.lab1.config.INTRUDER_DIR
import org.leti.lab1.config.PRIVATE_DIR
import org.leti.lab1.service.DirectoryInitializationService

class IntruderController {

    @FXML
    lateinit var sourceDirectoryViewer: TreeView<String>

    @FXML
    lateinit var intruderDirectoryViewer: TreeView<String>

    @FXML
    lateinit var copyValuableObjectButton: Button

    private val directoryInitializationService = DirectoryInitializationService()

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectoryTree(sourceDirectoryViewer, PRIVATE_DIR)
        reloadDirectoryTree(intruderDirectoryViewer, INTRUDER_DIR)
    }

    private fun reloadDirectoryTree(directoryViewer: TreeView<String>, path: String) {
        directoryInitializationService.initialize(directoryViewer, path)
    }
}