package org.leti.lab1.component

import org.leti.config.defaultDimension
import javax.swing.JPanel


/**
 * @author sstepanov
 */
internal class GPanel : JPanel() {

    init {
        preferredSize = defaultDimension
    }
}