package org.leti.geometry.primitive

data class Line<T>(val start: T, val end: T) {

    fun reverse(): Line<T> = Line(end, start)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Line<*>) return false
        return end == other.end && start == other.start || end == other.start && start == other.end
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

    companion object {
        fun generateRandom2D(): Line<Point2D> {
            return Line(Point2D.generateRandom(), Point2D.generateRandom())
        }

        fun generateRandom3D(): Line<Point3D> {
            return Line(Point3D.generateRandom(), Point3D.generateRandom())
        }
    }
}