package org.leti.lab6.util

import Jama.Matrix
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.primitive.Point3D
import kotlin.math.cos
import kotlin.math.sin

object RotationUtils {

    private const val DEGREES_TO_RADIANS = Math.PI / 180
    private const val ALPHA = 35.264
    private const val BETA = 45.0

    val baseRotation: Matrix
        get() {
            val matrixAlpha = arrayOf(
                    doubleArrayOf(1.0, 0.0, 0.0),
                    doubleArrayOf(0.0, cos(ALPHA * DEGREES_TO_RADIANS), sin(ALPHA * DEGREES_TO_RADIANS)),
                    doubleArrayOf(0.0, -sin(ALPHA * DEGREES_TO_RADIANS), cos(ALPHA * DEGREES_TO_RADIANS))
            )
            val matrixBeta = arrayOf(
                    doubleArrayOf(cos(BETA * DEGREES_TO_RADIANS), 0.0, -sin(BETA * DEGREES_TO_RADIANS)),
                    doubleArrayOf(0.0, 1.0, 0.0),
                    doubleArrayOf(sin(BETA * DEGREES_TO_RADIANS), 0.0, cos(BETA * DEGREES_TO_RADIANS))
            )
            return Matrix(matrixAlpha).times(Matrix(matrixBeta))
        }

    fun getXRotationMatrix(rotationMatrix: Matrix, alpha: Double): Matrix {
        val alphaa = alpha * DEGREES_TO_RADIANS
        val matrixAlpha = arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0),
                doubleArrayOf(0.0, cos(alphaa), sin(alphaa)),
                doubleArrayOf(0.0, -sin(alphaa), cos(alphaa))
        )
        return rotationMatrix.times(Matrix(matrixAlpha))
    }

    fun getYRotationMatrix(rotationMatrix: Matrix, beta: Double): Matrix {
        val betaa = beta * DEGREES_TO_RADIANS
        val matrixBeta = arrayOf(
                doubleArrayOf(cos(betaa), 0.0, -sin(betaa)),
                doubleArrayOf(0.0, 1.0, 0.0),
                doubleArrayOf(sin(betaa), 0.0, cos(betaa))
        )
        return rotationMatrix.times(Matrix(matrixBeta))
    }

    fun convert(point3D: Point3D, rotationMatrix: Matrix): Point3D {
        val pointArray = point3D.array
        val pointMatrix = arrayOf(
                doubleArrayOf(pointArray[0]),
                doubleArrayOf(pointArray[1]),
                doubleArrayOf(pointArray[2])
        )
        val rotationPoint = rotationMatrix.times(Matrix(pointMatrix)).array
        return Point3D(rotationPoint[0][0], rotationPoint[1][0], rotationPoint[2][0])
    }

    fun orthogonalProjection(point3D: Point3D): Point2D {
        return Point2D(point3D.x, point3D.y)
    }
}