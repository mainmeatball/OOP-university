package org.leti.lab4.launcher

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.leti.lab1.config.HEIGHT
import org.leti.lab1.config.WIDTH

private const val MAC_WINDOW_TITLE = "Mandatory access control"
private const val MAC_WINDOW_FORM = "lab4/mac-window.fxml"

class IntruderLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(MAC_WINDOW_FORM))
        primaryStage.apply {
            title = MAC_WINDOW_TITLE
            scene = Scene(root, WIDTH, HEIGHT)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(IntruderLauncher::class.java, *args)
}