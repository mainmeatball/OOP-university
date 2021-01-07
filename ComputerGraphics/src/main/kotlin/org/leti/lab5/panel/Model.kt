package org.leti.lab5.panel

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point2D
import org.leti.geometry.primitive.Point3D
import org.leti.geometry.shape.CubeRectangle
import org.leti.lab5.util.RotationUtils

object Model {
    fun determineVisibility(cubeLines: List<Line<Point3D>>, rectangles: List<CubeRectangle>) {
        val linesProjection = mutableMapOf<Line<Point3D>, Line<Point2D>>()
        val linesWithZ = mutableMapOf<Line<Point2D>, Double>()

        var maxZ = -Double.MAX_VALUE
        for (l in cubeLines) {
            val startZ = l.start.z
            val endZ = l.end.z
            val z = if (startZ >= endZ) startZ else endZ

            val start = RotationUtils.orthogonalProjection(l.start)
            val end = RotationUtils.orthogonalProjection(l.end)

            val projectionLine = Line(start, end)
            linesProjection[l] = projectionLine
            linesWithZ[projectionLine] = z
            if (z >= maxZ) {
                maxZ = z
            }
        }
        for (l in cubeLines) {
            if (linesWithZ[linesProjection[l]] == maxZ) {
                for (r in rectangles) {
                    if (r.contains(l)) {
                        r.isVisible = true
                    }
                }
            }
        }
    }
}