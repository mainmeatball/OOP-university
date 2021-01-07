package org.leti.lab5.listener

interface RotateListener {
    fun xRotate(alpha: Double)

    fun yRotate(beta: Double)

    fun setDefaultRotation()
}