package org.leti.lab6.listener

import org.leti.geometry.primitive.Line
import org.leti.geometry.primitive.Point3D

interface ControlPanelListener {
    fun setCubeLines(cubeLines: List<Line<Point3D>>)

    fun setVisibility(visible: Boolean)

    fun setColored(colored: Boolean)
}