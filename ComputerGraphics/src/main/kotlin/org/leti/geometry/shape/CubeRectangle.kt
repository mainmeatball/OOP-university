package org.leti.geometry.shape

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point3D
import org.leti.lab6.util.Texture
import java.awt.Color

class CubeRectangle(
    color: Color,
    private val line0: Line<Point3D>,
    private var line1: Line<Point3D>,
    private var line2: Line<Point3D>,
    private var line3: Line<Point3D>
) {
    var isVisible = false
    var texture = Texture(color)
        private set

    operator fun contains(line: Line<Point3D>): Boolean {
        return line0 == line || line1 == line || line2 == line || line3 == line
    }

    val points: List<Point3D>
        get() = mutableListOf<Point3D>().apply {
            add(line0.start)
            add(line1.start)
            add(line2.start)
            add(line3.start)
        }

    init {
        line1 = if (line1.start == line0.end) line1 else line1.reverse()
        line2 = if (line2.start == line1.end) line2 else line2.reverse()
        line3 = if (line3.start == line2.end) line3 else line3.reverse()
        isVisible = false
    }
}