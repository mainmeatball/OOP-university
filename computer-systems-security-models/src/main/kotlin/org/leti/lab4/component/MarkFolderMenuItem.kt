package org.leti.lab4.component

import javafx.event.EventHandler
import javafx.scene.control.MenuItem
import org.leti.lab1.component.DirectoryViewer

class MarkFolderMenuItem(
    text: String,
    directoryViewer: DirectoryViewer,
    addSecurity: (TypeAwareTreeItem) -> Unit
) : MenuItem(text) {
    init {
        onAction = EventHandler {
            val file = directoryViewer.selectedItem ?: return@EventHandler
            addSecurity(file)
        }
    }
}