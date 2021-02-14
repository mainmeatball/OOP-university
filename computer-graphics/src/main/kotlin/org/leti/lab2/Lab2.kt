package org.leti.lab2

import org.leti.config.defaultDimension
import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point2D
import org.leti.lab2.component.DraggablePoint
import org.leti.lab2.component.JPanel2D
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JSlider
import javax.swing.SwingConstants.HORIZONTAL
import kotlin.math.pow
import kotlin.system.exitProcess


/**
 * @author sstepanov
 */
class Lab2 : Runnable {

    private val lines = mutableListOf<Line<Point2D>>()
    private val points = mutableListOf<DraggablePoint>()
    private var capacity = 5

    override fun run() {
        val mainFrame = JFrame("Computer Graphics Lab 2").apply {
            size = defaultDimension
            addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent?) {
                    isVisible = false
                    exitProcess(0)
                }
            })
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        }
        val jPanel = JPanel2D().apply {
            preferredSize = defaultDimension
            background = Color.WHITE
            addMouseListener(object : MouseAdapter() {
                override fun mousePressed(mouse: MouseEvent) {
                    val g2D = graphics as Graphics2D
                    g2D.stroke = BasicStroke(3.0f)
                    if (points.size >= capacity) {
                        g2D.drawBezierCurve()
                        return
                    }
                    val draggablePoint = DraggablePoint(mouse.x, mouse.y) {
                        g2D.redrawBezier()
                    }
                    points.add(draggablePoint)
                    add(draggablePoint)
                    revalidate()
                    repaint()
                }
            })
        }
        mainFrame.contentPane = jPanel
        mainFrame.layout = null
        val label = JLabel("Количество узлов:").apply {
            setBounds(20, 20, 150, 20)
        }
        val slider = JSlider(HORIZONTAL, 3, 10, 5).apply {
            setBounds(10, 50, 150, 40)
            majorTickSpacing = 1
            minorTickSpacing = 1
            paintTicks = true
            paintLabels = true
            snapToTicks = true
            addChangeListener {
                capacity = value
            }
        }
        val resetButton = JButton("Очистить").apply {
            setBounds(30, 520, 100, 20)
            addActionListener {
                for (point in points) {
                    jPanel.remove(point)
                }
                jPanel.repaint()
                points.clear()
            }
        }
        jPanel.add(label)
        jPanel.add(slider)
        jPanel.add(resetButton)
        jPanel.revalidate()
        jPanel.repaint()
        mainFrame.isVisible = true
    }

    private fun Graphics2D.redrawBezier() {
        eraseLines()
        drawBezierCurve()
    }

    fun Graphics2D.drawBezierCurve() {
        val bezier = { t: Double -> calculateBezier(points, t) }
        val epsilon = 0.01
        var t = 0.0
        var prevBPoint = bezier(t)
        while (t < 1.0 - epsilon) {
            t += epsilon
            val bPoint = bezier(t)
            drawLine(prevBPoint, bPoint)
            prevBPoint = bPoint
        }
    }

    private fun Graphics2D.eraseLines() {
        for (line in lines) {
            eraseLine(line.start, line.end)
        }
        lines.clear()
    }

    private fun Graphics2D.eraseLine(start: Point2D, end: Point2D) {
        color = Color.WHITE
        drawLine(start.x.toInt(), start.y.toInt(), end.x.toInt(), end.y.toInt())
        color = Color.BLACK
    }

    private fun Graphics2D.drawLine(start: Point2D, end: Point2D) {
        lines.add(Line(start, end))
        drawLine(start.x.toInt(), start.y.toInt(), end.x.toInt(), end.y.toInt())
    }

    private fun calculateBezier(points: List<DraggablePoint>, t: Double): Point2D {
        var x = 0.0
        var y = 0.0
        for ((i, point) in points.withIndex()) {
            val n = points.lastIndex
            val coef = (fac(n) / (fac(i) * fac(n - i))) * (1.0 - t).pow(n - i) * t.pow(i)
            x += coef * (point.x + 5)
            y += coef * (point.y + 5)
        }
        return Point2D(x, y)
    }

    private tailrec fun fac(n: Int, accum: Long = 1): Long {
        if (n == 0) return 1
        val soFar = n * accum
        return if (n <= 1) {
            soFar
        } else {
            fac(n - 1, soFar)
        }
    }
}