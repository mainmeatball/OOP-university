package org.leti.lab3.listener

interface RotateListener {
    fun xRotate(alpha: Double)
    fun yRotate(beta: Double)
    fun setDefaultRotation()
}