package org.leti.lab5.launcher

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.leti.lab5.config.HEIGHT
import org.leti.lab5.config.WIDTH

private const val ROLE_WINDOW_TITLE = "Role access control"
private const val ROLE_WINDOW_FORM = "lab5/main-window.fxml"

class IntruderLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(ROLE_WINDOW_FORM))
        primaryStage.apply {
            title = ROLE_WINDOW_TITLE
            scene = Scene(root, WIDTH, HEIGHT)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(IntruderLauncher::class.java, *args)
}