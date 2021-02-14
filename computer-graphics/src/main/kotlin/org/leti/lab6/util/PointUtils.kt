package org.leti.lab6.util

import org.leti.geometry.primitive.Point3D
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author sstepanov
 */
fun distanceBetweenPoints(p1: Point3D, p2: Point3D): Double {
    return sqrt((p1.x - p2.x).pow(2.0) + (p1.y - p2.y).pow(2.0) + (p1.z - p2.z).pow(2.0))
}