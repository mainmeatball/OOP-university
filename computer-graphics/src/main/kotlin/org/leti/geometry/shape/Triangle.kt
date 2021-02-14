package org.leti.geometry.shape

import org.leti.lab1.enum.RotationType
import org.leti.lab1.util.rotate
import java.awt.Point

/**
 * @author sstepanov
 */
class Triangle : Cloneable {
    lateinit var point1: Point
        private set
    lateinit var point2: Point
        private set
    lateinit var point3: Point
        private set

    fun setPoint(point: Point) {
        if (!::point1.isInitialized) {
            point1 = point
        } else if (!::point2.isInitialized) {
            point2 = point
        } else if (!::point3.isInitialized) {
            point3 = point
        }
    }

    fun move(point1: Point, point2: Point, point3: Point) {
        this.point1 = point1
        this.point2 = point2
        this.point3 = point3
    }

    fun getXs() = intArrayOf(point1.x, point2.x, point3.x)
    fun getYs() = intArrayOf(point1.y, point2.y, point3.y)

    fun getPoints() = getXs().zip(getYs()).map { Point(it.first, it.second) }

    fun isInitialized() = ::point1.isInitialized
            && ::point2.isInitialized
            && ::point3.isInitialized

    fun rotate(rotationPoint: Point, angle: Double, rotationType: RotationType) {
        var realAngle = angle / 180f * Math.PI
        realAngle = when(rotationType) {
            RotationType.CLOCKWISE -> realAngle
            RotationType.ANTI_CLOCKWISE -> -realAngle
        }
        val point1 = point1.rotate(rotationPoint, realAngle)
        val point2 = point2.rotate(rotationPoint, realAngle)
        val point3 = point3.rotate(rotationPoint, realAngle)
        move(point1, point2, point3)
    }

    public override fun clone(): Triangle {
        val o = Triangle()
        o.point1 = point1
        o.point2 = point2
        o.point3 = point3
        return o
    }

    companion object {
        const val CIRCLE_RADIUS = 5
    }
}