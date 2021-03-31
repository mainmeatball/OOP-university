package org.leti.lab4.service

import javafx.scene.control.Label
import javafx.scene.paint.Color

object LoggerService {

    lateinit var label: Label

    fun log(message: String, color: Color = Color.BLACK) {
        label.text = message
        label.textFill = color
    }

    fun error(message: String) {
        label.text = message
        label.textFill = Color.RED
    }

    fun success(message: String) {
        label.text = message
        label.textFill = Color.GREEN
    }
}