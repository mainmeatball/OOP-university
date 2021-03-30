package org.leti.lab4.component

import javafx.scene.control.TreeItem
import org.leti.lab5.component.SecurityType

data class TypeAwareTreeItem(
    var value: String,
    var type: TreeItemType? = null,
    var security: SecurityType = SecurityType.NON_SECRET
) : TreeItem<String>(value) {
    var absolutePath: String = "/"
}

enum class TreeItemType {
    FOLDER, FILE
}