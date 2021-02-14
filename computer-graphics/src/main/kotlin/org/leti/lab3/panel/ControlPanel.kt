package org.leti.lab3.panel

import org.leti.geometry.primitive.Point3D
import org.leti.lab3.listener.ControlPanelListener
import org.leti.lab3.listener.RotateListener
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*
import kotlin.math.abs

class ControlPanel(private val controlPanelListener: ControlPanelListener) : JPanel() {
    private val isCurvePointMarked: JCheckBox
    private val isBaseLineVisible: JCheckBox

    private var pointXFields = emptyList<JTextField>()
    private var pointYFields = emptyList<JTextField>()
    private var pointZFields = emptyList<JTextField>()

    private val polyhedronVerticesDialog = PolyhedronVertices()

    fun setPointXFields(pointXFields: List<JTextField>) {
        this.pointXFields = pointXFields
    }

    fun setPointYFields(pointYFields: List<JTextField>) {
        this.pointYFields = pointYFields
    }

    fun setPointZFields(pointZFields: List<JTextField>) {
        this.pointZFields = pointZFields
    }

    private fun sendBasePoints() {
        val basePoints: MutableList<Point3D> = ArrayList()
        var x: Double
        var y: Double
        var z: Double
        for (i in pointXFields.indices) {
            x = pointXFields[i].text.toDouble()
            y = pointYFields[i].text.toDouble()
            z = pointZFields[i].text.toDouble()
            basePoints.add(Point3D(x, y, z))
        }
        controlPanelListener.setBasePoints(basePoints)
    }

    private val isPointsSets: Boolean
        get() = pointXFields.isNotEmpty() && pointYFields.isNotEmpty() && pointZFields.isNotEmpty()

    internal inner class ButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            when (e.actionCommand) {
                REPAINT_BUTTON_STR -> try {
                    if (isPointsSets) {
                        sendBasePoints()
                    } else {
                        throw NumberFormatException()
                    }
                } catch (exception: NumberFormatException) {
                    JOptionPane.showMessageDialog(null, "Заданы некорректные значения координат точек.")
                }
                DEFAULT_ROTATION_STR -> (controlPanelListener as RotateListener).setDefaultRotation()
                POLYHEDRON_VERTICES_STR -> polyhedronVerticesDialog.open()
            }
        }
    }

    internal inner class CheckBoxListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            if (e.actionCommand == BASE_POINT_STR) {
                controlPanelListener.setBaseLineVisible(isBaseLineVisible.isSelected)
            } else if (e.actionCommand == CURVE_POINT_STR) {
                controlPanelListener.setCurvePointMarked(isCurvePointMarked.isSelected)
            }
        }
    }

    internal inner class PolyhedronVertices : JDialog() {
        // начальные значения координат точек
        private val xValues = doubleArrayOf(-150.0, -150.0, -150.0, -150.0, -50.0, -50.0, -50.0, -50.0, 50.0, 50.0, 50.0, 50.0, 150.0, 150.0, 150.0, 150.0)
        private val yValues = doubleArrayOf(0.0, 50.0, 50.0, 0.0, 50.0, -300.0, 50.0, 50.0, 50.0, 50.0, 300.0, 50.0, 0.0, 50.0, 50.0, 0.0)
        private val zValues = doubleArrayOf(150.0, 50.0, -50.0, -150.0, 150.0, 50.0, -50.0, -150.0, 150.0, 50.0, -50.0, -150.0, 150.0, 50.0, -50.0, -150.0)
        private val pointXFields: MutableList<JTextField>
        private val pointYFields: MutableList<JTextField>
        private val pointZFields: MutableList<JTextField>
        fun open() {
            isVisible = true
        }

        private fun close() {
            isVisible = false
        }

        fun setPoints() {
            setPointXFields(pointXFields)
            setPointYFields(pointYFields)
            setPointZFields(pointZFields)
        }

        internal inner class PolyhedronVerticesButtonListener : ActionListener {
            override fun actionPerformed(e: ActionEvent) {
                when (e.actionCommand) {
                    Companion.SET_BASE_POINTS_STR -> {
                        setPoints()
                        close()
                    }
                    Companion.SET_DEFAULT_BASE_POINTS_STR -> {
                        for (i in 0..15) {
                            pointXFields[i].text = xValues[i].toString()
                            pointYFields[i].text = yValues[i].toString()
                            pointZFields[i].text = zValues[i].toString()
                        }
                    }
                    Companion.SET_RANDOM_BASE_POINTS_STR -> {
                        val size = 200.0
                        val startX = getRandomNumber(100.0)
                        val startY = getRandomNumber(100.0)
                        val startZ = getRandomNumber(100.0)
                        var x = startX
                        var y = startY
                        var z = startZ
                        for (i in pointXFields.indices) {
                            pointXFields[i].text = x.toString().substring(0, 5)
                            pointYFields[i].text = y.toString().substring(0, 5)
                            pointZFields[i].text = z.toString().substring(0, 5)
                            if (i == 3 || i == 7 || i == 11 || i == 15) {
                                x += abs(getRandomNumber(size))
                                z = startZ + getRandomNumber(50.0)
                            } else {
                                z -= abs(getRandomNumber(size))
                            }
                            y = getRandomNumber(size)
                        }
                    }
                }
            }

            private fun getRandomNumber(max: Double): Double {
                val sign = if (Math.random() > 0.5) -1 else 1
                return sign * (Math.random() * max)
            }
        }

        init {
            title = Companion.TITLE
            isModal = true
            size = Dimension(800, 200)
            isResizable = false
            defaultCloseOperation = DO_NOTHING_ON_CLOSE
            val buttonListener = PolyhedronVerticesButtonListener()
            val setBasePointsButton = JButton(Companion.SET_BASE_POINTS_STR)
            setBasePointsButton.addActionListener(buttonListener)
            val setDefaultPointsButton = JButton(Companion.SET_DEFAULT_BASE_POINTS_STR)
            setDefaultPointsButton.addActionListener(buttonListener)
            val setRandomBasePointsButton = JButton(Companion.SET_RANDOM_BASE_POINTS_STR)
            setRandomBasePointsButton.addActionListener(buttonListener)
            val buttonPanel = JPanel(GridLayout(1, 3))
            buttonPanel.add(setBasePointsButton)
            buttonPanel.add(setDefaultPointsButton)
            buttonPanel.add(setRandomBasePointsButton)
            pointXFields = ArrayList()
            pointYFields = ArrayList()
            pointZFields = ArrayList()
            for (i in 0..15) {
                pointXFields.add(JTextField(xValues[i].toString(), 6))
                pointYFields.add(JTextField(yValues[i].toString(), 6))
                pointZFields.add(JTextField(zValues[i].toString(), 6))
            }
            val coordinatesPanel = JPanel(GridLayout(4, 4, 10, 20))
            var currPanel: JPanel
            for (i in 0..15) {
                currPanel = JPanel(GridLayout(1, 3))
                currPanel.add(JLabel(i.toString()))
                currPanel.add(pointXFields[i])
                currPanel.add(pointYFields[i])
                currPanel.add(pointZFields[i])
                coordinatesPanel.add(currPanel)
            }
            layout = BorderLayout()
            add(coordinatesPanel, BorderLayout.CENTER)
            add(buttonPanel, BorderLayout.SOUTH)
            pack()
            isVisible = false
        }
    }

    companion object {
        private const val REPAINT_BUTTON_STR = "Нарисовать поверхность Безье"
        private const val BASE_POINT_STR = "Отображать точки многогранника"
        private const val CURVE_POINT_STR = "Отображать точки построения поверхности"
        private const val DEFAULT_ROTATION_STR = "Установить углы вращения по умолчанию"
        private const val POLYHEDRON_VERTICES_STR = "Задать координаты многогранника"
        private const val TITLE = "Меню ввода координат многогранника"
        private const val SET_BASE_POINTS_STR = "Задать вершины многогранника"
        private const val SET_DEFAULT_BASE_POINTS_STR = "Вершины по умолчанию"
        private const val SET_RANDOM_BASE_POINTS_STR = "Случайные координаты"
    }

    init {
        val buttonPanel = JPanel(GridLayout(2, 3, 10, 0))
        val buttonListener = ButtonListener()
        val checkBoxListener = CheckBoxListener()
        val repaintButton = JButton(REPAINT_BUTTON_STR)
        repaintButton.addActionListener(buttonListener)
        val setDefaultRotationButton = JButton(DEFAULT_ROTATION_STR)
        setDefaultRotationButton.addActionListener(buttonListener)
        val setPolyhedronVertices = JButton(POLYHEDRON_VERTICES_STR)
        setPolyhedronVertices.addActionListener(buttonListener)
        isBaseLineVisible = JCheckBox(BASE_POINT_STR, true)
        isBaseLineVisible.addActionListener(checkBoxListener)
        isCurvePointMarked = JCheckBox(CURVE_POINT_STR, true)
        isCurvePointMarked.addActionListener(checkBoxListener)
        buttonPanel.add(repaintButton)
        buttonPanel.add(setDefaultRotationButton)
        buttonPanel.add(setPolyhedronVertices)
        buttonPanel.add(isBaseLineVisible)
        buttonPanel.add(isCurvePointMarked)
        add(buttonPanel)
        polyhedronVerticesDialog.setPoints()
        sendBasePoints()
    }
}