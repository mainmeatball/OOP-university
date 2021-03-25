package org.leti.lab4.component

import javafx.scene.control.TreeItem

class TypeAwareTreeItem(
    var value: String,
    var type: TreeItemType? = null,
    var security: SecurityFolderType = SecurityFolderType.NON_SECRET
) : TreeItem<String>(value) {
    var absolutePath: String = "/"
}

enum class TreeItemType {
    FOLDER, FILE
}

enum class SecurityFolderType(
    val value: String,
    val privacy: Int
) {
    TOP_SECRET("Top-secret", Int.MAX_VALUE),
    SECRET("Secret", 500),
    NON_SECRET("Non-secret", Int.MIN_VALUE);

    companion object {
        fun getFromReadableValue(readableValue: String): SecurityFolderType {
            return values().first { it.value == readableValue }
        }
    }
}