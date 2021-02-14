package org.leti.geometry.primitive

import org.leti.lab6.util.distanceBetweenPoints

class PointLight(
    private val lightPoint: Point3D,
    private val intensity: Int
) {
    fun getLightToPoint(point: Point3D): Double {

//        System.out.println("dist " + dist);

//        return (int) (Double.valueOf(intensity) / Math.pow(dist, 2));
        return distanceBetweenPoints(lightPoint, point)
    }
}