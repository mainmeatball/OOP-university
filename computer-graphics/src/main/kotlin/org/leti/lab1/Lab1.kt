package org.leti.lab1

import org.leti.config.CENTER_X
import org.leti.config.HEIGHT
import org.leti.config.defaultDimension
import org.leti.lab1.enum.DrawType
import org.leti.lab1.enum.RotationType
import org.leti.geometry.shape.Triangle
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.border.BevelBorder
import kotlin.system.exitProcess


/**
 * @author sstepanov
 */
class Lab1 : Runnable {

    companion object {
        const val ROTATION_POINT_RADIUS = 2
        const val BUTTON_WIDTH = 30
        const val BUTTON_HEIGHT = 20
    }

    private lateinit var rotationPoint: Point
    private var prevTriangle = Triangle()
    private val triangle = Triangle()
    private var drawType = DrawType.TRIANGLE

    private lateinit var rotateClockwiseButton: JButton
    private lateinit var rotateAntiClockwiseButton: JButton

    override fun run() {
        val mainFrame = JFrame("Computer Graphics Lab 1")
        mainFrame.size = defaultDimension
        mainFrame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                mainFrame.isVisible = false
                exitProcess(0)
            }
        })
        mainFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val jPanel = JPanel()
        jPanel.preferredSize = defaultDimension
        jPanel.border = BorderFactory.createBevelBorder(BevelBorder.RAISED)
        jPanel.background = Color.WHITE
        jPanel.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(mouse: MouseEvent) {
                when (drawType) {
                    DrawType.TRIANGLE -> {
                        if (!triangle.isInitialized()) {
                            jPanel.graphics.drawCircle(mouse.x, mouse.y, Triangle.CIRCLE_RADIUS, Color.RED)
                            triangle.setPoint(Point(mouse.x, mouse.y))
                        }
                        if (triangle.isInitialized()) {
                            jPanel.graphics.connectDots(triangle)
                            drawType = DrawType.PLACE_ROTATION_POINT
                        }
                    }
                    DrawType.PLACE_ROTATION_POINT -> {
                        jPanel.graphics.fillCircle(mouse.x, mouse.y, ROTATION_POINT_RADIUS, Color.RED)
                        rotationPoint = Point(mouse.x, mouse.y)
                        rotateClockwiseButton.isEnabled = true
                        rotateAntiClockwiseButton.isEnabled = true
                        drawType = DrawType.MOVE_ROTATION_POINT
                    }
                    DrawType.MOVE_ROTATION_POINT -> {
                        jPanel.graphics.fillCircle(rotationPoint.x, rotationPoint.y, ROTATION_POINT_RADIUS, Color.WHITE)
                        jPanel.graphics.fillCircle(mouse.x, mouse.y, ROTATION_POINT_RADIUS, Color.RED)
                        rotationPoint = Point(mouse.x, mouse.y)
                    }
                }
            }
        })
        mainFrame.contentPane = jPanel
        mainFrame.layout = null
        val textField = JTextField(20).apply {
            setBounds(CENTER_X - 30, HEIGHT - BUTTON_HEIGHT - 40, BUTTON_WIDTH + 30, BUTTON_HEIGHT)
        }
        rotateClockwiseButton = JButton("<-").apply {
            setBounds(CENTER_X - BUTTON_WIDTH - 30, HEIGHT - BUTTON_HEIGHT - 40, BUTTON_WIDTH, BUTTON_HEIGHT)
            addActionListener {
                jPanel.graphics.actionListener(textField.text.toDouble(), RotationType.CLOCKWISE)
            }
            isEnabled = false
        }
        rotateAntiClockwiseButton = JButton("->").apply {
            setBounds(CENTER_X + BUTTON_WIDTH, HEIGHT - BUTTON_HEIGHT - 40, BUTTON_WIDTH, BUTTON_HEIGHT)
            addActionListener {
                jPanel.graphics.actionListener(textField.text.toDouble(), RotationType.ANTI_CLOCKWISE)
            }
            isEnabled = false
        }
        mainFrame.add(rotateClockwiseButton)
        mainFrame.add(rotateAntiClockwiseButton)
        mainFrame.add(textField)
        mainFrame.isVisible = true
    }

    private fun Graphics.actionListener(angle: Double, rotationType: RotationType) {
        eraseDashed(prevTriangle)
        erase(triangle)
        prevTriangle = triangle.clone()
        drawDashed(prevTriangle)
        triangle.rotate(rotationPoint, angle, rotationType)
        redraw(triangle)
    }

    private fun Graphics.erase(triangle: Triangle) {
        if (!triangle.isInitialized()) {
            return
        }
        color = Color.WHITE
        connectDots(triangle)
        eraseCircles(triangle)
        color = Color.BLACK
    }

    private fun Graphics.eraseDashed(triangle: Triangle) {
        if (!triangle.isInitialized()) {
            return
        }
        val g2d = create() as Graphics2D
        g2d.stroke = BasicStroke(1F)
        g2d.color = Color.WHITE
        g2d.drawPolygon(triangle.getXs(), triangle.getYs(), 3)
        g2d.dispose()
        g2d.connectDots(triangle)
        g2d.eraseCircles(triangle)
        color = Color.BLACK
    }

    private fun Graphics.redraw(triangle: Triangle) {
        val g2d = create() as Graphics2D
        g2d.stroke = BasicStroke(2F)
        drawPolygon(triangle.getXs(), triangle.getYs(), 3)
        g2d.dispose()
    }

    private fun Graphics.drawDashed(triangle: Triangle) {
        val g2d = create() as Graphics2D
        val dashed = BasicStroke(1F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0F, floatArrayOf(9F), 0F)
        g2d.stroke = dashed
        g2d.color = Color.BLUE
        g2d.drawPolygon(triangle.getXs(), triangle.getYs(), 3)
        g2d.dispose()
    }

    private fun Graphics.connectDots(triangle: Triangle) {
        drawPolygon(triangle.getXs(), triangle.getYs(), 3)
    }

    private fun Graphics.eraseCircles(triangle: Triangle) {
        triangle.getPoints().forEach {
            drawCircle(it.x, it.y, Triangle.CIRCLE_RADIUS, Color.WHITE)
        }
    }

    private fun Graphics.drawCircle(x: Int, y: Int, radius: Int, color: Color = Color.BLACK) {
        this.color = color
        drawOval(x - radius, y - radius, radius * 2, radius * 2)
        this.color = Color.BLACK
    }

    private fun Graphics.fillCircle(x: Int, y: Int, radius: Int, color: Color = Color.BLACK) {
        this.color = color
        fillOval(x - radius, y - radius, radius * 2, radius * 2)
        this.color = Color.BLACK
    }
}