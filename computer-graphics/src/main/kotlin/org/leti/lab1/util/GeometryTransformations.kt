package org.leti.lab1.util

import java.awt.Point
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author sstepanov
 */
fun Point.rotate(rotationPoint: Point, angle: Double): Point {
        val cos = cos(angle)
        val sin = sin(angle)
        return Point(
                rotationPoint.x + ((this.x - rotationPoint.x) * cos - (this.y - rotationPoint.y) * sin).toInt(),
                rotationPoint.y + ((this.x - rotationPoint.x) * sin + (this.y - rotationPoint.y) * cos).toInt()
        )
}