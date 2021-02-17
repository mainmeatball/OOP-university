package org.leti.lab1.service

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import java.io.File


class DirectoryInitializationService {

    fun initialize(directoryViewer: TreeView<String>, directory: String) {
        val dir = File(directory)
        directoryViewer.root = getNodesForDirectory(dir, true)
    }

    private fun getNodesForDirectory(directory: File, isRoot: Boolean = false): TreeItem<String> {
        val dirName = if (isRoot) directory.absolutePath else directory.name
        val root = TreeItem(dirName).apply {
            isExpanded = true
        }
        directory.listFiles()?.let {
            for (f in it) {
                if (f.isDirectory) {
                    root.children.add(getNodesForDirectory(f))
                } else {
                    root.children.add(TreeItem(f.name))
                }
            }
        }
        return root
    }
}