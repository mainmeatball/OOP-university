package org.leti.lab1.controller.user

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TreeView

private const val MAIN_MENU = "user/main-menu.fxml"

class ValuableObjectCopyController {

    @FXML
    lateinit var directoryViewer: TreeView<String>

    @FXML
    lateinit var fileName: TextField

    @FXML
    lateinit var directoryName: TextField

    @FXML
    lateinit var backToMenuButton: Button

    @FXML
    lateinit var copyValuableObjectButton: Button

    @FXML
    private fun switchBackToMenu(event: ActionEvent) {
        val newScene = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(MAIN_MENU))
        backToMenuButton.scene.root = newScene
        event.consume()
    }

    @FXML
    private fun copyValuableObject(event: ActionEvent) {
        println("File name: ${fileName.text}")
        println("Directory name: ${directoryName.text}")
        event.consume()
    }

}