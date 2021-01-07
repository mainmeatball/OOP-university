package org.leti.lab4.panel

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.shape.Rectangle
import org.leti.lab4.listener.ClearWindowListener
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import javax.swing.JPanel

/**
 * @author sstepanov
 */
class DrawPanel(width: Int, height: Int) : JPanel(), ClearWindowListener {

    val lines = mutableListOf<Line<Point2D>>()
    var rect: Rectangle? = null

    init {
        preferredSize = Dimension(width, height)
        background = Color.WHITE
        layout = null
    }

    fun eraseData() {
        lines.clear()
    }

    override fun clearWindow() {
        val graphics2D = graphics as Graphics2D
        graphics2D.color = Color.WHITE
        graphics2D.fillRect(0, 0, width, height)
    }

    fun paintLines(n: Int) {
        clearWindow()
        lines.clear()
        val graphics2D = graphics as Graphics2D
        for (i in 0 until n) {
            val line = Line.generateRandom2D()
            lines.add(line)
        }
        graphics2D.draw(lines)
        if (rect != null) {
            graphics2D.drawRect(rect!!)
        }
    }

    fun drawRectangle() {
        val graphics2D = graphics as Graphics2D
        clearWindow()
        graphics2D.draw(lines)
        rect = Rectangle.generateRandom()
        graphics2D.drawRect(rect!!)
    }

    private fun Graphics2D.drawRect(rect: Rectangle, color: Color = Color.RED) {
        stroke = BasicStroke(3.0f)
        this.color = color
        val vert = rect.vertices
        check(vert.size == 4)
        drawRect(
                rect.leftBottomVertex.x.toInt(),
                rect.leftBottomVertex.y.toInt(),
                rect.width.toInt(),
                rect.height.toInt()
        )
        if (lines.isEmpty()) {
            return
        }
        for (line in lines) {
            if (line.isSimplyOut(rect)) {
                drawLine(line, Color.GREEN)
                continue
            }
            if (line.isFullyIn(rect)) {
                drawLine(line, Color.BLUE)
                continue
            }
            val ints = line.checkInt(rect)
            if (ints.size == 1) {
                val lineInside =
                        if (!rect.has(line.start) && !rect.has(line.end))
                            Line(ints[0], ints[0])
                        else if (rect.has(line.start))
                            Line(line.start, ints[0])
                        else Line(line.end, ints[0])
                drawLine(lineInside, Color.MAGENTA)
            }
            if (ints.size == 2) {
                val lineInside = Line(ints[0], ints[1])
                drawLine(lineInside, Color.MAGENTA)
            }
        }
    }

    private fun Line<Point2D>.isSimplyOut(rect: Rectangle): Boolean {
        val xc = start.x
        val yc = start.y
        val xd = end.x
        val yd = end.y
        val xl = rect.leftBottomVertex.x
        val xr = rect.leftBottomVertex.x + rect.width
        val yv = rect.leftUpperVertex.y
        val yn = rect.leftUpperVertex.y - rect.height
        return (xc < xl && xd < xl) ||
                (xc > xr && xd > xr) ||
                (yc < yn && yd < yn) ||
                (yc > yv && yd > yv)
    }

    private fun Line<Point2D>.isFullyIn(rect: Rectangle): Boolean {
        val xc = start.x
        val yc = start.y
        val xd = end.x
        val yd = end.y
        val xl = rect.leftBottomVertex.x
        val xr = rect.leftBottomVertex.x + rect.width
        val yv = rect.leftUpperVertex.y
        val yn = rect.leftUpperVertex.y - rect.height
        var allOk = 0
        if (xc <= xl || xc >= xr) allOk++
        if (xd <= xl || xd >= xr) allOk++
        if (yc <= yn || yc >= yv) allOk++
        if (yd <= yn || yd >= yv) allOk++
        return allOk == 0
    }

    private fun Line<Point2D>.checkInt(rect: Rectangle): MutableList<Point2D> {
        val intersections = mutableListOf<Point2D>()
        val lx = end.x - start.x
        val ly = end.y - start.y
        val t1 = (rect.xMin - start.x) / lx
        if (t1 in 0.0..1.0) {
            val intPoint = Point2D(rect.xMin, start.y + ly * t1)
            if (intPoint.y >= rect.yMin && intPoint.y <= rect.yMax) {
                intersections.add(intPoint)
            }
        }
        val t2 = (rect.xMax - start.x) / lx
        if (t2 in 0.0..1.0) {
            val intPoint = Point2D(rect.xMax, start.y + ly * t2)
            if (intPoint.y >= rect.yMin && intPoint.y <= rect.yMax) {
                intersections.add(intPoint)
            }
        }
        val t3 = (rect.yMin - start.y) / ly
        if (t3 in 0.0..1.0) {
            val intPoint = Point2D(start.x + lx * t3, rect.yMin)
            if (intPoint.x >= rect.xMin && intPoint.x <= rect.xMax) {
                intersections.add(intPoint)
            }
        }
        val t4 = (rect.yMax - start.y) / ly
        if (t4 in 0.0..1.0) {
            val intPoint = Point2D(start.x + lx * t4, rect.yMax)
            if (intPoint.x >= rect.xMin && intPoint.x <= rect.xMax) {
                intersections.add(intPoint)
            }
        }
        return intersections
    }

    private fun getIntersection(l1: Line<Point2D>, l2: Line<Point2D>): Point2D {
        val x1 = l1.start.x
        val x2 = l1.end.x
        val x3 = l2.start.x
        val x4 = l2.end.x
        val y1 = l1.start.y
        val y2 = l1.end.y
        val y3 = l2.start.y
        val y4 = l2.end.y
        val intx = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4))
        val inty = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4))
        return Point2D(intx, inty)
    }

    private fun Graphics2D.draw(lines: List<Line<Point2D>>, color: Color = Color.BLACK) {
        stroke = BasicStroke(2.0f)
        this.color = color
        for (line in lines) {
            drawLine(line)
        }
    }

    private fun Graphics2D.drawLine(line: Line<Point2D>, color: Color = Color.BLACK) {
        stroke = BasicStroke(2.0f)
        this.color = color
        drawLine(line.start.x.toInt(), line.start.y.toInt(), line.end.x.toInt(), line.end.y.toInt())
    }
}