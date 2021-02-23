package org.leti.lab1.service

import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.leti.lab1.component.DirectoryViewer
import java.io.File


class DirectoryInitializationService {

    private val fileIcon = Image(javaClass.classLoader.getResourceAsStream("images/file_icon.png"))
    private val folderIcon = Image(javaClass.classLoader.getResourceAsStream("images/folder_icon.png"))

    fun initialize(directoryViewer: DirectoryViewer, directory: String) {
        val dir = File(directory)
        val prevDirectory = TreeItem("..")
        val directoryTree = getNodesForDirectory(dir)
        val hiddenRoot = TreeItem("hidden_root").apply {
            children.addAll(prevDirectory, directoryTree)
        }
        directoryViewer.root = hiddenRoot
        directoryViewer.isShowRoot = false
        directoryViewer.currentDirectory = dir.absolutePath

        directoryViewer.onMouseClicked = EventHandler {
            if (it.clickCount == 2) {
                val item = directoryViewer.selectionModel.selectedItem
                if (item.value == "..") {
                    val prevDir = getPreviousDirectory(directory)
                    initialize(directoryViewer, prevDir)
                } else {
                    val selectedFile = File(directory + File.separator + item.value)
                    if (!selectedFile.isDirectory) {
                        return@EventHandler
                    }
                    initialize(directoryViewer, selectedFile.absolutePath)
                }
            }
        }
    }

    private fun getNodesForDirectory(directory: File): TreeItem<String> {
        val root = TreeItem(directory.absolutePath).apply {
            isExpanded = true
        }
        directory.listFiles()?.let {
            for (f in it) {
                if (f.isDirectory) {
                    val dir = TreeItem(f.name).apply {
                        graphic = ImageView(folderIcon)
                    }
                    root.children.add(dir)
                } else {
                    val file = TreeItem(f.name).apply {
                        graphic = ImageView(fileIcon)
                    }
                    root.children.add(file)
                }
            }
        }
        return root
    }

    private fun getPreviousDirectory(directory: String): String {
        val separators = directory.count { it == '/' }
        if (separators <= 1) {
            return directory
        }
        return directory.substringBeforeLast('/')
    }
}