package org.leti.lab4

import org.leti.config.HEIGHT
import org.leti.config.WIDTH
import org.leti.lab4.panel.ControlPanel
import org.leti.lab4.panel.DrawPanel
import org.leti.lab4.panel.MainWindow

/**
 * @author sstepanov
 */
class Lab4 : Runnable {
    override fun run() {
        val drawPanel = DrawPanel(WIDTH, HEIGHT)
        val controlPanel = ControlPanel(drawPanel)
        MainWindow("Computer Graphics Lab 4", WIDTH, HEIGHT, drawPanel, controlPanel)
    }
}