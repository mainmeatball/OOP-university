package org.leti.lab6.listener

interface RotateListener {
    fun xRotate(alpha: Double)

    fun yRotate(beta: Double)

    fun setDefaultRotation()
}