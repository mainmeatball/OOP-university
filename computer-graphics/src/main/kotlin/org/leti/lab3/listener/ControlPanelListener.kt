package org.leti.lab3.listener

import org.leti.geometry.primitive.Point3D

interface ControlPanelListener {
    fun setBasePoints(basePoints: List<Point3D>)
    fun setBaseLineVisible(visible: Boolean)
    fun setCurvePointMarked(marked: Boolean)
}