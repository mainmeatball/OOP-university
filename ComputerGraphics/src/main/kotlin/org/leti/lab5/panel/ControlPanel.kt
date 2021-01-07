package org.leti.lab5.panel

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point3D
import org.leti.lab5.listener.ControlPanelListener
import org.leti.lab5.listener.ControlPanelSpinnerListener
import org.leti.lab5.listener.RotateListener
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JPanel

class ControlPanel(
    private val controlPanelListener: ControlPanelListener,
    private val spinnerListener: ControlPanelSpinnerListener
) : JPanel() {

    // начальные значения координат вершин куба
    private val xValues = doubleArrayOf(-CUBE_LENGTH, -CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH, -CUBE_LENGTH, -CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH)
    private val yValues = doubleArrayOf(CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH, -CUBE_LENGTH, -CUBE_LENGTH, -CUBE_LENGTH, -CUBE_LENGTH)
    private val zValues = doubleArrayOf(-CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH, -CUBE_LENGTH, -CUBE_LENGTH, CUBE_LENGTH, CUBE_LENGTH, -CUBE_LENGTH)
    private val isVisible: JCheckBox
    private val isSpinned: JCheckBox
    private fun sendCubeLines() {
        val cubePoints = mutableListOf<Point3D>()
        for (i in 0 until POINT_COUNT) {
            cubePoints.add(Point3D(xValues[i], yValues[i], zValues[i]))
        }
        val cubeLines = mutableListOf<Line<Point3D>>().apply {
            add(Line(cubePoints[0], cubePoints[1]))
            add(Line(cubePoints[1], cubePoints[2]))
            add(Line(cubePoints[2], cubePoints[3]))
            add(Line(cubePoints[3], cubePoints[0]))
            add(Line(cubePoints[4], cubePoints[5]))
            add(Line(cubePoints[5], cubePoints[6]))
            add(Line(cubePoints[6], cubePoints[7]))
            add(Line(cubePoints[7], cubePoints[4]))
            add(Line(cubePoints[0], cubePoints[4]))
            add(Line(cubePoints[1], cubePoints[5]))
            add(Line(cubePoints[2], cubePoints[6]))
            add(Line(cubePoints[3], cubePoints[7]))
        }
        controlPanelListener.setCubeLines(cubeLines)
    }

    internal inner class ButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            if (e.actionCommand == DEFAULT_ROTATION_STR) {
                (controlPanelListener as RotateListener).setDefaultRotation()
            }
        }
    }

    internal inner class CheckBoxListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when (e.actionCommand) {
                VISIBLE_LINE_STR -> controlPanelListener.setVisibility(!isVisible.isSelected)
                SPINNER_STR -> spinnerListener.pauseOrRestart()
            }
        }
    }

    companion object {
        private const val DEFAULT_ROTATION_STR = "Установить углы вращения по умолчанию"
        private const val VISIBLE_LINE_STR = "Только видимые грани и ребра"
        private const val SPINNER_STR = "Вращение куба"

        // количество вершин куба
        private const val POINT_COUNT = 8

        private const val CUBE_LENGTH = 100.0
    }

    init {
        val buttonPanel = JPanel(GridLayout(2, 3))
        val buttonListener = ButtonListener()
        val checkBoxListener = CheckBoxListener()
        val setDefaultRotationButton = JButton(DEFAULT_ROTATION_STR)
        setDefaultRotationButton.addActionListener(buttonListener)
        isVisible = JCheckBox(VISIBLE_LINE_STR, false)
        isVisible.addActionListener(checkBoxListener)
        isSpinned = JCheckBox(SPINNER_STR, true)
        isSpinned.addActionListener(checkBoxListener)
        buttonPanel.add(setDefaultRotationButton)
        buttonPanel.add(isVisible)
        buttonPanel.add(isSpinned)
        add(buttonPanel)
        sendCubeLines()
    }
}