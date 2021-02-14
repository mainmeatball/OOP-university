package org.leti.lab3.panel

import Jama.Matrix
import org.leti.geometry.primitive.Point3D
import java.util.*
import kotlin.math.pow

object Model {
    private const val T_STEP = 0.05 // шаг переменной t (t принадлежит [0, 1])
    private const val W_STEP = 0.05 // шаг переменной w (w принадлежит [0, 1])
    private val basisMatrix = arrayOf(doubleArrayOf(-1.0, 3.0, -3.0, 1.0), doubleArrayOf(3.0, -6.0, 3.0, 0.0), doubleArrayOf(-3.0, 3.0, 0.0, 0.0), doubleArrayOf(1.0, 0.0, 0.0, 0.0))

    fun plotBezierSurface(basePoints: List<Point3D>): MutableList<MutableList<Point3D>> {
        /**
         * Расположение точек:
         *
         * 0 1 2 3
         * 4 5 6 7
         * 8 9 10 11
         * 12 13 14 15
         */
        val x_matrix = Array(4) { DoubleArray(4) }
        val y_matrix = Array(4) { DoubleArray(4) }
        val z_matrix = Array(4) { DoubleArray(4) }
        for (r in 0..3) {
            for (c in 0..3) {
                x_matrix[r][c] = basePoints[r * 4 + c].x
                y_matrix[r][c] = basePoints[r * 4 + c].y
                z_matrix[r][c] = basePoints[r * 4 + c].z
            }
        }
        val basis = Matrix(basisMatrix)
        val basePointsXMatrixChanged: Matrix = basis.times(Matrix(x_matrix)).times(basis)
        val basePointsYMatrixChanged: Matrix = basis.times(Matrix(y_matrix)).times(basis)
        val basePointsZMatrixChanged: Matrix = basis.times(Matrix(z_matrix)).times(basis)
        val t_matrix = Array(1) { DoubleArray(4) }
        val w_matrix = Array(4) { DoubleArray(1) }
        val linesOfSurface = mutableListOf<MutableList<Point3D>>()
        var t = 0.0
        while (t <= 1.0) {

            // заполняем матрицу t (строка)
            for (i in 0..3) {
                t_matrix[0][i] = Math.pow(t, (3 - i).toDouble())
            }
            val currLine: MutableList<Point3D> = ArrayList<Point3D>()
            val t_jama_matrix = Matrix(t_matrix)
            var w = 0.0
            while (w <= 1.0) {

                // заполняем матрицу w (столбец)
                for (i in 0..3) {
                    w_matrix[i][0] = w.pow((3 - i).toDouble())
                }
                val w_jama_matrix = Matrix(w_matrix)
                val x: Double = t_jama_matrix.times(basePointsXMatrixChanged).times(w_jama_matrix).get(0, 0)
                val y: Double = t_jama_matrix.times(basePointsYMatrixChanged).times(w_jama_matrix).get(0, 0)
                val z: Double = t_jama_matrix.times(basePointsZMatrixChanged).times(w_jama_matrix).get(0, 0)
                currLine.add(Point3D(x, y, z))
                w += W_STEP
            }
            linesOfSurface.add(currLine)
            t += T_STEP
        }
        return linesOfSurface
    }
}