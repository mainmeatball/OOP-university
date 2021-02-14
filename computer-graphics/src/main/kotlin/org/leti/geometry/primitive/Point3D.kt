package org.leti.geometry.primitive

import org.leti.config.HEIGHT
import org.leti.config.WIDTH
import kotlin.random.Random

class Point3D(x: Double, y: Double, val z: Double) : Point2D(x, y) {
    override val array: DoubleArray
        get() = doubleArrayOf(x, y, z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point3D) return false
        if (other.z.compareTo(z) != 0) return false
        if (other.x.compareTo(x) != 0) return false
        return other.y.compareTo(y) == 0
    }

    override fun hashCode(): Int {
        val temp = java.lang.Double.doubleToLongBits(z)
        return (temp xor (temp ushr 32)).toInt()
    }

    companion object {
        fun generateRandom(): Point3D {
            val x = Random.nextDouble(WIDTH.toDouble())
            val y = Random.nextDouble(HEIGHT.toDouble())
            val z = Random.nextDouble(HEIGHT.toDouble())
            return Point3D(x, y, z)
        }
    }
}