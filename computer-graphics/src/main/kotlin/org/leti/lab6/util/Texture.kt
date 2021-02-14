package org.leti.lab6.util

import org.leti.geometry.primitive.Point3D
import org.leti.geometry.primitive.PointLight
import org.leti.geometry.shape.CubeRectangle
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*

class Texture(private val baseColor: Color) : BufferedImage(WIDTH, HEIGHT, IMAGE_TYPE) {
    private val g: Graphics = graphics
    private var pointLight: PointLight? = null
    fun setPointLight(pointLight: PointLight) {
        this.pointLight = pointLight
    }

    private fun repaint(rectangle: CubeRectangle) {
        val points = rectangle.points
        val p0 = points[0]
        val p1 = points[1]
        val p2 = points[2]
        val p3 = points[3]
        val p0light = pointLight!!.getLightToPoint(p0)
        val p1light = pointLight!!.getLightToPoint(p1)
        val p2light = pointLight!!.getLightToPoint(p2)
        val p3light = pointLight!!.getLightToPoint(p3)
        val lights = doubleArrayOf(p0light, p1light, p2light, p3light)
        Arrays.sort(lights)
        val pointsLight: MutableMap<Double, Point3D> = HashMap()
        pointsLight[p0light] = p0
        pointsLight[p1light] = p1
        pointsLight[p2light] = p2
        pointsLight[p3light] = p3

        g.color = baseColor
        val gp = GradientPaint(p0.x.toFloat(), p0.y.toFloat(), baseColor, p3.x.toFloat(), p3.y.toFloat(), Color.BLACK)
        (graphics as Graphics2D).paint = gp
        graphics.fillRect(0, 0, WIDTH, HEIGHT)
    }

    fun getTexturePaint(polygon: Polygon, rectangle: CubeRectangle): TexturePaint {
        repaint(rectangle)
        return TexturePaint(this, polygon.bounds2D)
    }

    companion object {
        private const val WIDTH = 100
        private const val HEIGHT = 100
        private const val IMAGE_TYPE = TYPE_INT_RGB
    }
}