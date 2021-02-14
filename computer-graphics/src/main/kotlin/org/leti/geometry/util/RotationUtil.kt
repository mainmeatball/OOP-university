package org.leti.geometry.util

import Jama.Matrix
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.primitive.Point3D
import kotlin.math.cos
import kotlin.math.sin

object RotationUtil {
    private const val DEGREES_TO_RADIANS = Math.PI / 180
    private const val ALPHA = 35.264
    private const val BETA = 45.0

    @JvmStatic
    fun reverseByY(currBasePoint: Point3D): Point3D {
        return Point3D(currBasePoint.x, -currBasePoint.y, currBasePoint.z)
    }

    @JvmStatic
    val baseRotation: Matrix
        get() {
            val matrixAlpha = arrayOf(doubleArrayOf(1.0, 0.0, 0.0), doubleArrayOf(0.0, cos(ALPHA * DEGREES_TO_RADIANS), sin(ALPHA * DEGREES_TO_RADIANS)), doubleArrayOf(0.0, -sin(ALPHA * DEGREES_TO_RADIANS), cos(ALPHA * DEGREES_TO_RADIANS)))
            val matrixBeta = arrayOf(doubleArrayOf(cos(BETA * DEGREES_TO_RADIANS), 0.0, -sin(BETA * DEGREES_TO_RADIANS)), doubleArrayOf(0.0, 1.0, 0.0), doubleArrayOf(sin(BETA * DEGREES_TO_RADIANS), 0.0, cos(BETA * DEGREES_TO_RADIANS)))
            return Matrix(matrixAlpha).times(Matrix(matrixBeta))
        }

    @JvmStatic
    fun getXRotationMatrix(rotationMatrix: Matrix, alpha: Double): Matrix {
        var a = alpha
        a *= DEGREES_TO_RADIANS
        val matrixAlpha = arrayOf(doubleArrayOf(1.0, 0.0, 0.0), doubleArrayOf(0.0, cos(alpha), sin(alpha)), doubleArrayOf(0.0, -sin(alpha), cos(alpha)))
        return rotationMatrix.times(Matrix(matrixAlpha))
    }

    @JvmStatic
    fun getYRotationMatrix(rotationMatrix: Matrix, beta: Double): Matrix {
        var b = beta
        b *= DEGREES_TO_RADIANS
        val matrixBeta = arrayOf(doubleArrayOf(cos(beta), 0.0, -sin(beta)), doubleArrayOf(0.0, 1.0, 0.0), doubleArrayOf(sin(beta), 0.0, cos(beta)))
        return rotationMatrix.times(Matrix(matrixBeta))
    }

    @JvmStatic
    fun convert(point3D: Point3D, rotationMatrix: Matrix): Point3D {
        val pointArray: DoubleArray = point3D.array
        val pointMatrix = arrayOf(doubleArrayOf(pointArray[0]), doubleArrayOf(pointArray[1]), doubleArrayOf(pointArray[2]))
        val rotationPoint = rotationMatrix.times(Matrix(pointMatrix)).array
        return Point3D(rotationPoint[0][0], rotationPoint[1][0], rotationPoint[2][0])
    }

    @JvmStatic
    fun orthogonalProjection(point3D: Point3D): Point2D {
        return Point2D(point3D.x, point3D.y)
    }
}