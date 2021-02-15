package org.leti.lab1.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TreeView

private const val USER_MAIN_WINDOW = "user-main-window.fxml"

class UserValuableObjectCreationWindowController {

    @FXML
    lateinit var directoryViewer: TreeView<String>

    @FXML
    lateinit var backToMenuButton: Button

    @FXML
    lateinit var fileName: Button

    @FXML
    lateinit var text: Button

    @FXML
    lateinit var createValuableObjectButton: Button

    @FXML
    private fun switchBackToMenu(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(USER_MAIN_WINDOW))
        backToMenuButton.scene.root = newScene
        event.consume()
    }

}