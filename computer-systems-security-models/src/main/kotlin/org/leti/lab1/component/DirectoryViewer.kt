package org.leti.lab1.component

import javafx.scene.control.TreeView
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.config.DESKTOP

open class DirectoryViewer : TreeView<String>() {
    var currentDirectory = DESKTOP

    val selectedItem: TypeAwareTreeItem?
        get() = selectionModel.selectedItem as TypeAwareTreeItem?
}