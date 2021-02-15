package org.leti.lab1.launcher.intruder

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.leti.lab1.config.HEIGHT
import org.leti.lab1.config.WIDTH

private const val INTRUDER_WINDOW_TITLE = "Intruder Memory Stream"
private const val INTRUDER_WINDOW_FORM = "intruder/main-menu.fxml"

class IntruderLauncher : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource(INTRUDER_WINDOW_FORM))
        primaryStage.apply {
            title = INTRUDER_WINDOW_TITLE
            scene = Scene(root, WIDTH, HEIGHT)
        }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(IntruderLauncher::class.java, *args)
}