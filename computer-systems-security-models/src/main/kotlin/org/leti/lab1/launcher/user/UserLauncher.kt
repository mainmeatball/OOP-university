package org.leti.lab1.launcher.user

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class UserLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("user-main-window.fxml"))
        primaryStage.apply {
            title = "User Memory Stream"
            scene = Scene(root, 300.0, 275.0)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(UserLauncher::class.java, *args)
}