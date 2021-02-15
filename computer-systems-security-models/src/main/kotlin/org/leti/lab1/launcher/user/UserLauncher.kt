package org.leti.lab1.launcher.user

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.leti.lab1.config.HEIGHT
import org.leti.lab1.config.WIDTH

private const val USER_WINDOW_TITLE = "User Memory Stream"
private const val USER_WINDOW_FORM = "user-main-window.fxml"

class UserLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(USER_WINDOW_FORM))
        primaryStage.apply {
            title = USER_WINDOW_TITLE
            scene = Scene(root, WIDTH, HEIGHT)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(UserLauncher::class.java, *args)
}