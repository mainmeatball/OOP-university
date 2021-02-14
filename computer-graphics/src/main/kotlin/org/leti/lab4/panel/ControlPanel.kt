package org.leti.lab4.panel

import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField

class ControlPanel(val drawPanel: DrawPanel) : JPanel() {

    companion object {
        private const val CLEAR_WINDOW = "Очистить"
        private const val DRAW_LINES = "Сгенерировать линии"
        private const val DRAW_POLYGON = "Установить прямоугольное окно"
    }

    private val numberOfLines: JTextField

    init {
        val buttonPanel = JPanel(GridLayout(2, 3, 10, 0))
        val buttonListener = ButtonListener()
        val repaintButton = JButton(CLEAR_WINDOW).apply {
            addActionListener(buttonListener)
        }
        numberOfLines = JTextField("100")
        val drawLinesButton = JButton(DRAW_LINES).apply {
            addActionListener(buttonListener)
        }
        val drawPolygonButton = JButton(DRAW_POLYGON).apply {
            addActionListener(buttonListener)
        }

        buttonPanel.add(repaintButton)
        buttonPanel.add(numberOfLines)
        buttonPanel.add(drawPolygonButton)
        buttonPanel.add(drawLinesButton)
        add(buttonPanel)
    }

    inner class ButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when(e.actionCommand) {
                CLEAR_WINDOW -> {
                    drawPanel.clearWindow()
                    drawPanel.eraseData()
                }
                DRAW_LINES -> {
                    drawPanel.paintLines(numberOfLines.text.toInt())
                }
                DRAW_POLYGON -> {
                    drawPanel.drawRectangle()
                }
            }
        }
    }
}