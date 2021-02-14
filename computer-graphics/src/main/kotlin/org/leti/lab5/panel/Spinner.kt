package org.leti.lab5.panel

import org.leti.lab5.listener.ControlPanelSpinnerListener
import org.leti.lab5.listener.RotateListener


class Spinner(private val rotateListener: RotateListener, private val period: Long) : Runnable, ControlPanelSpinnerListener {
    private val isRunning = true
    private var isPaused = false
    override fun run() {
        var count = 0
        while (isRunning) {
            if (!isPaused) {
                when {
                    count < 25 -> {
                        rotateListener.yRotate(5.0)
                        count++
                    }
                    count < 50 -> {
                        rotateListener.xRotate(5.0)
                        count++
                    }
                    else -> count = 0
                }
            }
            try {
                Thread.sleep(period)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun pauseOrRestart() {
        isPaused = !isPaused
    }
}