package org.leti.lab1.service

import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.service.TreeItemTypeMarkerService
import java.io.File


open class DirectoryInitializationService {

    protected val treeItemTypeService = TreeItemTypeMarkerService

    fun initialize(directoryViewer: DirectoryViewer, directory: String) {
        val dir = File(directory)
        val prevDirectory = TreeItem("..")
        val directoryTree = getNodesForDirectory(dir)
        val hiddenRoot = TreeItem("hidden_root").apply {
            children.addAll(prevDirectory, directoryTree)
        }
        directoryViewer.apply {
            root = hiddenRoot
            isShowRoot = false
            currentDirectory = dir.absolutePath
        }

        directoryViewer.onMouseClicked = EventHandler {
            if (it.clickCount == 2) {
                val item = directoryViewer.selectionModel.selectedItem
                    ?: return@EventHandler
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
        treeItemTypeService.updateState()
    }

    private fun getNodesForDirectory(directory: File): TreeItem<String> {
        val securityType = treeItemTypeService.resolveSecurityType(directory.absolutePath)
        val root = TypeAwareTreeItem("(${securityType.name}) " + directory.absolutePath).apply {
            isExpanded = true
        }
        if (!allowDirectory(root)) {
            return TypeAwareTreeItem("You are not allowed to see this directory")
        }
        directory.listFiles()?.let {
            for (f in it) {
                if (f.isDirectory) {
                    val dir = TypeAwareTreeItem(f.name).apply {
                        absolutePath = directory.absolutePath + File.separator + f.name
                        type = TreeItemType.FOLDER
                    }
                    treeItemTypeService.markAsNonSecret(dir, getAvailableSecurityTypes())
                    if (allowDirectory(dir)) {
                        root.children.add(dir)
                    }
                } else {
                    val file = TypeAwareTreeItem(f.name).apply {
                        absolutePath = directory.absolutePath + File.separator + f.name
                        type = TreeItemType.FILE
                    }
                    treeItemTypeService.markAsFile(file)
                    root.children.add(file)
                }
            }
        }
        return root
    }

    protected open fun getAvailableSecurityTypes() = listOf("Top-secret", "Secret", "Non-secret")

    protected open fun allowDirectory(dir: TypeAwareTreeItem): Boolean {
        return true
    }

    private fun getPreviousDirectory(directory: String): String {
        val separators = directory.count { it == '/' }
        if (separators <= 1) {
            return directory
        }
        return directory.substringBeforeLast('/')
    }
}