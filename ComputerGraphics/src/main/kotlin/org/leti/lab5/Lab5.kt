package org.leti.lab5

import org.leti.config.HEIGHT
import org.leti.config.WIDTH
import org.leti.lab5.panel.ControlPanel
import org.leti.lab5.panel.DrawPanel
import org.leti.lab5.panel.MainWindow
import org.leti.lab5.panel.Spinner

/**
 * @author sstepanov
 */
class Lab5 : Runnable {
    override fun run() {
        val drawPanel = DrawPanel(WIDTH, HEIGHT)
        val spinner = Spinner(drawPanel, PERIOD)
        val controlPanel = ControlPanel(drawPanel, spinner)
        MainWindow("Computer Graphics Lab 5", WIDTH, HEIGHT, drawPanel, controlPanel)
        Thread(spinner).start()
    }

    companion object {
        const val PERIOD: Long = 50
    }
}