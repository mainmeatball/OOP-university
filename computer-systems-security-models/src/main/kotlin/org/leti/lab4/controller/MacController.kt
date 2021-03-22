package org.leti.lab4.controller

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Text
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileService
import org.leti.lab4.component.SecurityFolderType
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.service.TreeItemTypeMarkerService
import java.io.File


class MacController {

    private val treeItemTypeService = TreeItemTypeMarkerService

    @FXML
    lateinit var sourceDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var targetDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var copyFileButton: Button

    @FXML
    lateinit var status: Label

    private val directoryInitializationService = DirectoryInitializationService()

    private val fileService = FileService()

    @FXML
    private fun copyFile(event: ActionEvent) {
        val selectedItem = sourceDirectoryViewer.selectedItem ?: run {
            log("Please, select file to copy")
            return
        }
        if (selectedItem.type == TreeItemType.FOLDER) {
            log("You can not copy a directory")
            return
        }
        val absoluteSourcePath = selectedItem.absolutePath
        val absoluteTargetPath = targetDirectoryViewer.currentDirectory + File.separator + sourceDirectoryViewer.selectedItem!!.value
        val fromSecurity = treeItemTypeService.resolveSecurityType(sourceDirectoryViewer.currentDirectory)
        val toSecurity = treeItemTypeService.resolveSecurityType(targetDirectoryViewer.currentDirectory)
        if (!validateSecurity(fromSecurity, toSecurity)) {
            log("File copy from $fromSecurity to $toSecurity folder is not allowed", Color.RED)
            return
        }
        log("File \"${sourceDirectoryViewer.selectedItem!!.value}\" successfully copied", Color.GREEN)
        fileService.copy(absoluteSourcePath, absoluteTargetPath)
        reloadTargetDirectory()
        event.consume()
    }

    @FXML
    fun initialize() {
        reloadDirectories()
        val sourceContextMenu = createContextMenu(sourceDirectoryViewer)
        val targetContextMenu = createContextMenu(targetDirectoryViewer)
        sourceDirectoryViewer.contextMenu = sourceContextMenu
        targetDirectoryViewer.contextMenu = targetContextMenu
    }

    private fun reloadDirectories() {
        reloadSourceDirectory()
        reloadTargetDirectory()
    }

    private fun reloadSourceDirectory() {
        reloadDirectoryTree(sourceDirectoryViewer, sourceDirectoryViewer.currentDirectory)
    }

    private fun reloadTargetDirectory() {
        reloadDirectoryTree(targetDirectoryViewer, targetDirectoryViewer.currentDirectory)
    }

    private fun reloadDirectoryTree(directoryViewer: DirectoryViewer, path: String) {
        directoryInitializationService.initialize(directoryViewer, path)
    }

    private fun createContextMenu(directoryViewer: DirectoryViewer): ContextMenu {
        return ContextMenu(
            createMenuItem("Top-secret", directoryViewer) {
                treeItemTypeService.markAsTopSecretFolder(it, true)
                reloadDirectories()
            },
            createMenuItem("Secret", directoryViewer) {
                treeItemTypeService.markAsSecretFolder(it, true)
                reloadDirectories()
            },
            createMenuItem("Non-secret", directoryViewer) {
                treeItemTypeService.markAsNonSecretFolder(it, true)
                reloadDirectories()
            }
        )
    }

    private fun createMenuItem(
        text: String,
        directoryViewer: DirectoryViewer,
        addSecurity: (TypeAwareTreeItem) -> Unit
    ): MenuItem {
        return MenuItem(text).apply {
            onAction = EventHandler {
                val file = directoryViewer.selectedItem ?: return@EventHandler
                addSecurity(file)
            }
        }
    }

    private fun validateSecurity(from: SecurityFolderType, to: SecurityFolderType) = from.privacy <= to.privacy

    private fun log(message: String, color: Color = Color.BLACK) {
        status.text = message
        status.textFill = color
    }
}