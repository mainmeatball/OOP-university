package org.leti.lab6.panel

import Jama.Matrix
import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.primitive.Point3D
import org.leti.geometry.primitive.PointLight
import org.leti.geometry.shape.CubeRectangle
import org.leti.lab6.listener.ControlPanelListener
import org.leti.lab6.listener.RotateListener
import org.leti.lab6.util.RotationUtils
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException
import javax.swing.JPanel

class DrawPanel(
    private val panelWidth: Int,
    private val panelHeight: Int
) : JPanel(), RotateListener, ControlPanelListener {

    private val intense = 1000
    private val pointLight: PointLight = PointLight(Point3D(0.0, 0.0, 0.0), intense)
    private var alpha = 0.0
    private var beta = 0.0
    private var rotationMatrix: Matrix? = null
    private val defaultRoatationMatrix: Matrix
    private var cubeLines: List<Line<Point3D>>? = null
    private var isAllLineVisible = true
    private var isColored = false

    init {
        val drawPanelMouseListener = DrawPanelMouseListener(this, panelWidth, panelHeight)
        addMouseListener(drawPanelMouseListener)
        addMouseMotionListener(drawPanelMouseListener)
        updateRotationMatrix()
        defaultRoatationMatrix = rotationMatrix!!
    }

    override fun paint(g: Graphics) {

        // заливка фона
        g.color = BACKGROUND_COLOR
        g.fillRect(0, 0, panelWidth, panelHeight)

        // смещение цетра координатных осей на центр панели
        val g2d: Graphics2D = g as Graphics2D
        val offsetToRightDownCorner = AffineTransform()
        offsetToRightDownCorner.translate(width - AXIS_LENGTH, height - 200 - AXIS_LENGTH)
        g2d.transform(offsetToRightDownCorner)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // рисуем координатные оси
        val zeroPoint = Point3D(.0, .0, .0)
        val xAxis = Point3D(AXIS_LENGTH, .0, .0)
        val yAxis = Point3D(.0, -AXIS_LENGTH, .0)
        val zAxis = Point3D(.0, .0, AXIS_LENGTH)
        drawAxes(g, zeroPoint, xAxis, yAxis, zAxis)
        try {
            g2d.transform(offsetToRightDownCorner.createInverse())
        } catch (e: NoninvertibleTransformException) {
            e.printStackTrace()
        }
        val offsetToCenter = AffineTransform()
        offsetToCenter.translate(panelWidth / 2.0, (panelHeight - 100.0) / 2.0)
        g2d.transform(offsetToCenter)
        if (isAllLineVisible) {
            // рисуем куб без обработки видимости ребер
            if (cubeLines != null) {
                g.setColor(MAIN_COLOR)
                for (l in cubeLines!!) {
                    val start = RotationUtils.orthogonalProjection(RotationUtils.convert(l.start, rotationMatrix!!))
                    val end = RotationUtils.orthogonalProjection(RotationUtils.convert(l.end, rotationMatrix!!))
                    drawLine(g, start, end)
                }
            }
        } else {
            // отображение только видимых ребер
            val cubeLinesAfterRotation = mutableListOf<Line<Point3D>>()
            for (l in cubeLines!!) {
                val start: Point3D = RotationUtils.convert(l.start, rotationMatrix!!)
                val end: Point3D = RotationUtils.convert(l.end, rotationMatrix!!)
                cubeLinesAfterRotation.add(Line(start, end))
            }
            val rectangles = mutableListOf<CubeRectangle>()
            val l1 = cubeLinesAfterRotation[0]
            val l2 = cubeLinesAfterRotation[1]
            val l3 = cubeLinesAfterRotation[2]
            val l4 = cubeLinesAfterRotation[3]
            val l5 = cubeLinesAfterRotation[4]
            val l6 = cubeLinesAfterRotation[5]
            val l7 = cubeLinesAfterRotation[6]
            val l8 = cubeLinesAfterRotation[7]
            val l9 = cubeLinesAfterRotation[8]
            val l10 = cubeLinesAfterRotation[9]
            val l11 = cubeLinesAfterRotation[10]
            val l12 = cubeLinesAfterRotation[11]
            rectangles.add(CubeRectangle(Color.RED, l1, l2, l3, l4))
            rectangles.add(CubeRectangle(Color.BLUE, l5, l6, l7, l8))
            rectangles.add(CubeRectangle(Color.GREEN, l1, l10, l5, l9))
            rectangles.add(CubeRectangle(Color.LIGHT_GRAY, l2, l11, l6, l10))
            rectangles.add(CubeRectangle(Color.CYAN, l3, l12, l7, l11))
            rectangles.add(CubeRectangle(Color.YELLOW, l4, l9, l8, l12))
            Model.determineVisibility(cubeLinesAfterRotation, rectangles)
            val visibleRectangles = mutableListOf<CubeRectangle>()
            for (r in rectangles) {
                if (r.isVisible) {
                    visibleRectangles.add(r)
                }
            }
            for (r in visibleRectangles) {
                val points = r.points
                if (isColored) {
                    val xPoints = IntArray(points.size)
                    val yPoints = IntArray(points.size)
                    for (i in points.indices) {
                        val currPoint = RotationUtils.orthogonalProjection(points[i])
                        xPoints[i] = currPoint.x.toInt()
                        yPoints[i] = currPoint.y.toInt()
                    }
                    val polygon = Polygon(xPoints, yPoints, points.size)
                    val texture = r.texture
                    texture.setPointLight(pointLight)
                    val paint: TexturePaint = texture.getTexturePaint(polygon, r)
                    g2d.paint = paint
                    g.fillPolygon(polygon)
                } else {
                    g.setColor(MAIN_COLOR)
                    drawLine(g, points[0], points[1])
                    drawLine(g, points[1], points[2])
                    drawLine(g, points[2], points[3])
                    drawLine(g, points[3], points[0])
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
        rotationMatrix = RotationUtils.baseRotation
    }

    private fun updateRotationMatrix() {
        rotationMatrix = RotationUtils.baseRotation
    }

    private fun updateXRotationMatrix() {
        rotationMatrix = RotationUtils.getXRotationMatrix(rotationMatrix!!, alpha)
    }

    private fun updateYRotationMatrix() {
        rotationMatrix = RotationUtils.getYRotationMatrix(rotationMatrix!!, beta)
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
        var xAxisCopy = xAxis
        var yAxisCopy = yAxis
        var zAxisCopy = zAxis
        xAxisCopy = RotationUtils.convert(xAxisCopy, defaultRoatationMatrix)
        yAxisCopy = RotationUtils.convert(yAxisCopy, defaultRoatationMatrix)
        zAxisCopy = RotationUtils.convert(zAxisCopy, defaultRoatationMatrix)
        val zeroPoint2D = RotationUtils.orthogonalProjection(zeroPoint)
        val xAxis2D = RotationUtils.orthogonalProjection(xAxisCopy)
        val yAxis2D = RotationUtils.orthogonalProjection(yAxisCopy)
        val zAxis2D = RotationUtils.orthogonalProjection(zAxisCopy)
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

    override fun setCubeLines(cubeLines: List<Line<Point3D>>) {
        this.cubeLines = cubeLines
    }

    override fun setVisibility(visible: Boolean) {
        isAllLineVisible = visible
        repaint()
    }

    override fun setColored(colored: Boolean) {
        isColored = colored
        repaint()
    }

    companion object {
        private const val AXIS_LENGTH = 100.0
        private val BACKGROUND_COLOR = Color.BLACK
        private val MAIN_COLOR = Color.WHITE
    }
}

internal class DrawPanelMouseListener(
    private val rotateListener: RotateListener,
    private val maxX: Int,
    private val maxY: Int
) : MouseAdapter() {

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

    override fun mouseDragged(e: MouseEvent) {
        endX = e.x
        endY = e.y
        rotate()
        startX = endX
        startY = endY
    }

    private fun rotate() {
        val alpha: Double
        val beta: Double
        val xDelta = startX - endX.toDouble()
        val yDelta = endY - startY.toDouble()
        beta = xDelta / maxX * 90
        rotateListener.yRotate(beta)
        alpha = yDelta / maxY * 90
        rotateListener.xRotate(alpha)
    }

}