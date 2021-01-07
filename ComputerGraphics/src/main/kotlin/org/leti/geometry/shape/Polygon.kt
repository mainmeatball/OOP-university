package org.leti.geometry.shape

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point2D

/**
 * @author sstepanov
 */
open class Polygon(
        vararg points: Point2D
) {
    val vertices: Array<out Point2D> = points
}

class Rectangle(
        vararg points: Point2D
) : Polygon(*points) {

    val leftUpperVertex = Point2D(vertices.minOf { it.x }, vertices.maxOf { it.y })

    val leftBottomVertex = Point2D(vertices.minOf { it.x }, vertices.minOf { it.y })

    val rightUpperVertex = Point2D(vertices.maxOf { it.x }, vertices.maxOf { it.y })

    val rightBottomVertex = Point2D(vertices.maxOf { it.x }, vertices.minOf { it.y })

    val leftSide = Line(leftUpperVertex, leftBottomVertex)

    val rightSide = Line(rightUpperVertex, rightBottomVertex)

    val upSide = Line(leftUpperVertex, rightUpperVertex)

    val bottomSide = Line(leftBottomVertex, rightBottomVertex)

    val xMax = rightUpperVertex.x

    val xMin = leftBottomVertex.x

    val yMax = rightUpperVertex.y

    val yMin = rightBottomVertex.y

    val width = vertices.maxOf { it.x } - vertices.minOf { it.x }

    val height = vertices.maxOf { it.y } - vertices.minOf { it.y }

    fun has(point: Point2D): Boolean {
        return (point.x > xMin && point.x < xMax && point.y > yMin && point.y < yMax)
    }

    companion object {
        fun generateRandom(): Rectangle {
            val a = Point2D.generateRandom()
            val b = Point2D.generateRandom()
            val c = Point2D(a.x, b.y)
            val d = Point2D(b.x, a.y)
            return Rectangle(a, b, c, d)
        }
    }
}