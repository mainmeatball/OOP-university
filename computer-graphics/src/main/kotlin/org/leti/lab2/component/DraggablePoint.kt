package org.leti.lab2.component

import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JComponent
import javax.swing.border.LineBorder


/**
 * @author sstepanov
 */
class DraggablePoint(
        @Volatile var myX: Int,
        @Volatile var myY: Int,
        val callback: () -> Unit
) : JComponent() {

    @Volatile private var screenX = 0
    @Volatile private var screenY = 0

    init {
        border = LineBorder(Color.RED, 3, true)
        background = Color.WHITE
        setBounds(myX - 5, myY - 5, 10, 10)
        addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {}
            override fun mousePressed(e: MouseEvent) {
                screenX = e.xOnScreen
                screenY = e.yOnScreen
                myX = x
                myY = y
            }

            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })
        addMouseMotionListener(object : MouseMotionListener {
            override fun mouseDragged(e: MouseEvent) {
                val deltaX: Int = e.xOnScreen - screenX
                val deltaY: Int = e.yOnScreen - screenY
                setLocation(myX + deltaX, myY + deltaY)
            }
            override fun mouseMoved(e: MouseEvent) {}
        })
    }

    override fun paintComponent(g: Graphics) {
        callback()
        super.paintComponent(g)
    }
}