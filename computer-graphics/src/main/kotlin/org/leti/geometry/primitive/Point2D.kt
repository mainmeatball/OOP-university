package org.leti.geometry.primitive

import org.leti.config.HEIGHT
import org.leti.config.WIDTH
import kotlin.random.Random


open class Point2D(var x: Double, var y: Double) {
    open val array: DoubleArray
        get() = doubleArrayOf(x, y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point2D) return false
        return if (other.x.compareTo(x) != 0) false else other.y.compareTo(y) == 0
    }

    override fun hashCode(): Int {
        var result: Int
        var temp: Long = java.lang.Double.doubleToLongBits(x)
        result = (temp xor (temp ushr 32)).toInt()
        temp = java.lang.Double.doubleToLongBits(y)
        result = 31 * result + (temp xor (temp ushr 32)).toInt()
        return result
    }

    companion object {
        fun generateRandom(): Point2D {
            val x = Random.nextDouble(WIDTH.toDouble())
            val y = Random.nextDouble(HEIGHT.toDouble())
            return Point2D(x, y)
        }
    }
}