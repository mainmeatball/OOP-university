package org.leti.lab3.panel

import Jama.Matrix
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.primitive.Point3D
import org.leti.geometry.util.RotationUtil
import org.leti.lab3.listener.ControlPanelListener
import org.leti.lab3.listener.RotateListener
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform
import javax.swing.JPanel
import kotlin.math.abs

class DrawPanel(width: Int, height: Int) : JPanel(), RotateListener, ControlPanelListener {
    private val w: Int
    private val h: Int
    private var alpha = 0.0
    private var beta = 0.0
    private val defaultRotationMatrix: Matrix
    private var rotationMatrix = RotationUtil.baseRotation
    private var basePoints: List<Point3D>? = null
    private var curveLines: MutableList<MutableList<Point3D>>? = null
    private var isBaseLineVisible = true
    private var isCurvePointMarked = true

    override fun paint(g: Graphics) {

        // заливка фона белым цветом
        g.color = Color.WHITE
        g.fillRect(0, 0, width, height)

        // смещение цетра координатных осей на центр панели
        val g2d = g as Graphics2D
        val offsetToCenter = AffineTransform()
        offsetToCenter.translate((width / 2f).toDouble(), (height / 2f).toDouble())
        g2d.transform(offsetToCenter)

        // рисуем координатные оси
        val zeroPoint = Point3D(.0, .0, .0)
        val xAxis = Point3D(AXIS_LENGTH, .0, .0)
        val yAxis = Point3D(.0, -AXIS_LENGTH, .0)
        val zAxis = Point3D(.0, .0, AXIS_LENGTH)
        drawAxes(g, zeroPoint, xAxis, yAxis, zAxis)


        // расчитываем точки поверхности Безье по базисным точкам
        if (basePoints?.isNotEmpty() == true) {
            curveLines = Model.plotBezierSurface(basePoints!!)
        }

        // рисуем точки поверхности Безье
        g.setColor(Color.GRAY)
        if (curveLines != null) {
            val size = curveLines!!.size
            for (i in 0 until size) {
                val line: MutableList<Point3D> = ArrayList<Point3D>()
                for (j in 0 until size) {
                    line.add(curveLines!![j][i])
                }
                curveLines!!.add(line)
            }
            for (line in curveLines!!) {
                var prevPoint: Point2D? = null
                var currentPoint: Point2D
                for (i in 0 until line.size) {
                    val p = line[i]
                    line[i] = Point3D(p.x, -p.y, p.z)
                    currentPoint = RotationUtil.orthogonalProjection(RotationUtil.convert(p, rotationMatrix))
                    if (isCurvePointMarked) {
                        drawPointWithMark(g, currentPoint)
                    }
                    if (prevPoint != null) {
                        drawLine(g, prevPoint, currentPoint)
                    }
                    prevPoint = currentPoint
                }
            }
        }
        // рисуем базисный многоугольник по точкам
        if (isBaseLineVisible) {
            g.setColor(Color.BLACK)
            if (basePoints != null) {
                val basePointsCopy: List<Point3D> = ArrayList(basePoints!!)
                for (i in basePointsCopy.indices) {
                    var currBasePoint: Point3D = basePointsCopy[i]
                    currBasePoint = RotationUtil.reverseByY(currBasePoint)
                    val currBasePoint2D: Point2D = RotationUtil.orthogonalProjection(RotationUtil.convert(currBasePoint,
                        rotationMatrix))
                    drawPointWithMark(g, currBasePoint2D)
                    drawString(g, Point2D(currBasePoint2D.x + 10, currBasePoint2D.y + 10), i.toString())
                    var isNotEndOfRow = true
                    for (j in 1 until POLYHEDRON_SIZE) {
                        if (i == POLYHEDRON_SIZE * j - 1) {
                            isNotEndOfRow = false
                            break
                        }
                    }
                    if (isNotEndOfRow && i + 1 < basePointsCopy.size) {
                        drawLine(g, currBasePoint2D, RotationUtil.orthogonalProjection(
                            RotationUtil.convert(RotationUtil.reverseByY(basePointsCopy[i + 1]), rotationMatrix)))
                    }
                    if (i + 4 < basePointsCopy.size) {
                        drawLine(g, currBasePoint2D, RotationUtil.orthogonalProjection(RotationUtil.convert(
                            RotationUtil.reverseByY(basePointsCopy[i + 4]), rotationMatrix)))
                    }
                }
            }
        }
    }

    override fun xRotate(alpha: Double) {
        this.alpha = alpha
        updateXRotationMatrix()
        repaint()
    }

    override fun yRotate(beta: Double) {
        this.beta = beta
        updateYRotationMatrix()
        repaint()
    }

    override fun setDefaultRotation() {
        alpha = 0.0
        beta = 0.0
        setDefaultRotationMatrix()
        repaint()
    }

    private fun setDefaultRotationMatrix() {
        rotationMatrix = RotationUtil.baseRotation
    }

    private fun updateXRotationMatrix() {
        rotationMatrix = RotationUtil.getXRotationMatrix(rotationMatrix, alpha)
    }

    private fun updateYRotationMatrix() {
        rotationMatrix = RotationUtil.getYRotationMatrix(rotationMatrix, beta)
    }

    private fun drawPointWithMark(g: Graphics, p: Point2D) {
        drawLine(g, p, p)
        val x = p.x.toInt()
        val y = p.y.toInt()
        g.drawRect(x - POINT_SIZE / 2, y - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE)
    }

    private fun drawLine(g: Graphics, p1: Point2D, p2: Point2D) {
        val x1 = p1.x.toInt()
        val y1 = p1.y.toInt()
        val x2 = p2.x.toInt()
        val y2 = p2.y.toInt()
        g.drawLine(x1, y1, x2, y2)
    }

    private fun drawString(g: Graphics, p: Point2D, str: String) {
        val x = p.x.toInt()
        val y = p.y.toInt()
        g.drawString(str, x, y)
    }

    private fun drawAxes(g: Graphics, zeroPoint: Point3D, xAxis: Point3D, yAxis: Point3D, zAxis: Point3D) {
        val x = RotationUtil.convert(xAxis, defaultRotationMatrix)
        val y = RotationUtil.convert(yAxis, defaultRotationMatrix)
        val z = RotationUtil.convert(zAxis, defaultRotationMatrix)
        val zeroPoint2D = RotationUtil.orthogonalProjection(zeroPoint)
        val xAxis2D = RotationUtil.orthogonalProjection(x)
        val yAxis2D = RotationUtil.orthogonalProjection(y)
        val zAxis2D = RotationUtil.orthogonalProjection(z)
        g.color = Color.RED
        drawLine(g, zeroPoint2D, xAxis2D)
        drawString(g, xAxis2D, "X")
        g.color = Color.GREEN
        drawLine(g, zeroPoint2D, yAxis2D)
        drawString(g, yAxis2D, "Y")
        g.color = Color.BLUE
        drawLine(g, zeroPoint2D, zAxis2D)
        drawString(g, zAxis2D, "Z")
    }

    override fun setBasePoints(basePoints: List<Point3D>) {
        this.basePoints = basePoints
        repaint()
    }

    override fun setBaseLineVisible(visible: Boolean) {
        isBaseLineVisible = visible
        repaint()
    }

    override fun setCurvePointMarked(marked: Boolean) {
        isCurvePointMarked = marked
        repaint()
    }

    companion object {
        private const val AXIS_LENGTH = 350.0
        private const val POINT_SIZE = 6
        private const val POLYHEDRON_SIZE = 4
    }

    init {
        preferredSize = Dimension(width, height)
        addMouseListener(DrawPanelMouseListener(this, width, height))
        this.w = width
        this.h = height
        defaultRotationMatrix = rotationMatrix
        basePoints = ArrayList<Point3D>()
    }
}

internal class DrawPanelMouseListener(private val rotateListener: RotateListener, private val maxX: Int, private val maxY: Int) : MouseAdapter() {
    private var startX = 0
    private var startY = 0
    private var endX = 0
    private var endY = 0

    override fun mousePressed(e: MouseEvent) {
        startX = e.x
        startY = e.y
    }

    override fun mouseReleased(e: MouseEvent) {
        endX = e.x
        endY = e.y
        rotate()
    }

    private fun rotate() {
        val alpha: Double
        val beta: Double
        val dx = (endX - startX).toDouble()
        val dy = (endY - startY).toDouble()
        if (abs(dx) > abs(dy)) {
            beta = dx / maxX * 90
            rotateListener.yRotate(beta)
        } else {
            alpha = dy / maxY * 90
            rotateListener.xRotate(alpha)
        }
    }
}