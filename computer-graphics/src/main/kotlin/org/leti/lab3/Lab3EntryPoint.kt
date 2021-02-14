package org.leti.lab3

import org.leti.config.HEIGHT
import org.leti.config.WIDTH
import org.leti.lab3.panel.ControlPanel
import org.leti.lab3.panel.DrawPanel
import org.leti.lab3.panel.MainWindow
import javax.swing.SwingUtilities

/**
 * @author sstepanov
 */
fun main() {
    val drawPanel = DrawPanel(WIDTH + 100, HEIGHT)
    val controlPanel = ControlPanel(drawPanel)
    MainWindow("Computer Graphics Lab 3", WIDTH + 100, HEIGHT, drawPanel, controlPanel)
}