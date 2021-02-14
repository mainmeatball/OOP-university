package org.leti.lab1.launcher.intruder

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class IntruderLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("intruder-main-window.fxml"))
        primaryStage.apply {
            title = "Intruder Memory Stream"
            scene = Scene(root, 300.0, 275.0)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(IntruderLauncher::class.java, *args)
}