package org.leti.lab1.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Button

private const val VALUABLE_OBJECT_CREATION_WINDOW = "user-valuable-object-creation-window.fxml"
private const val VALUABLE_OBJECT_COPY_WINDOW = "user-valuable-object-copy-window.fxml"

class UserMainWindowController {

    @FXML
    lateinit var openValuableObjectCreationSceneButton: Button

    @FXML
    lateinit var openValuableObjectCopySceneButton: Button

    @FXML
    private fun openValuableObjectCreationScene(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(VALUABLE_OBJECT_CREATION_WINDOW))
        openValuableObjectCreationSceneButton.scene.root = newScene
        event.consume()
    }

    @FXML
    private fun openValuableObjectCopyScene(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(VALUABLE_OBJECT_COPY_WINDOW))
        openValuableObjectCopySceneButton.scene.root = newScene
        event.consume()
    }
}