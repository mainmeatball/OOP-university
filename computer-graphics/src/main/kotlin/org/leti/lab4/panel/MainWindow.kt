package org.leti.lab4.panel

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

class MainWindow(
        title: String,
        width: Int,
        height: Int,
        drawPanel: DrawPanel,
        controlPanel: ControlPanel
) : JFrame(title) {

    init {
        size = Dimension(width, height + 200)
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        add(drawPanel, BorderLayout.CENTER)
        add(controlPanel, BorderLayout.SOUTH)
        pack()
        isVisible = true
    }
}