package org.leti.lab4.controller

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileService
import org.leti.lab4.component.SecurityFolderType
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.service.TreeItemTypeMarkerService
import java.io.File


open class MacController {

    @FXML
    lateinit var sourceDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var targetDirectoryViewer: DirectoryViewer

    @FXML
    lateinit var copyFileButton: Button

    @FXML
    lateinit var createDirectoryButton: Button

    @FXML
    lateinit var status: Label

    @FXML
    lateinit var securityTypeDropdown: ComboBox<String>

    @FXML
    lateinit var newDirectoryName: TextField

    private val treeItemTypeService = TreeItemTypeMarkerService

    protected open val directoryInitializationService = DirectoryInitializationService()

    private val fileService = FileService()

    lateinit var lastChosenFilesystem: DirectoryViewer

    @FXML
    open fun initialize() {
        refreshDirectories()
        val sourceContextMenu = createContextMenu(sourceDirectoryViewer)
        val targetContextMenu = createContextMenu(targetDirectoryViewer)
        sourceDirectoryViewer.contextMenu = sourceContextMenu
        targetDirectoryViewer.contextMenu = targetContextMenu

        lastChosenFilesystem = sourceDirectoryViewer
        sourceDirectoryViewer.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            lastChosenFilesystem = sourceDirectoryViewer
        }
        targetDirectoryViewer.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            lastChosenFilesystem = targetDirectoryViewer
        }
    }

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
        refreshTargetDirectory()
        event.consume()
    }

    @FXML
    fun createDirectory() {
        val selection = securityTypeDropdown.selectionModel?.selectedItem!!
        val securityFolderType = SecurityFolderType.valueOf(selection.replace('-', '_').toUpperCase())
        val newFolderName = newDirectoryName.text ?: run {
            log("Please, enter new directory name")
            return
        }
        try {
            val newDirPath = lastChosenFilesystem.currentDirectory + File.separator + newFolderName
            fileService.createFolder(newDirPath)
            treeItemTypeService.updateCache(newDirPath, securityFolderType)
            log("Directory $newFolderName is successfully created", Color.GREEN)
        } catch (ex: FileAlreadyExistsException) {
            log("Directory is already exists, fill another name")
        }
        refreshDirectories()
    }

    protected fun refreshDirectories() {
        refreshSourceDirectory()
        refreshTargetDirectory()
    }

    private fun refreshSourceDirectory() {
        refreshDirectoryTree(sourceDirectoryViewer, sourceDirectoryViewer.currentDirectory)
    }

    private fun refreshTargetDirectory() {
        refreshDirectoryTree(targetDirectoryViewer, targetDirectoryViewer.currentDirectory)
    }

    private fun refreshDirectoryTree(directoryViewer: DirectoryViewer, path: String) {
        directoryInitializationService.initialize(directoryViewer, path)
    }

    private fun createContextMenu(directoryViewer: DirectoryViewer): ContextMenu {
        return ContextMenu(
            createMenuItem("Top-secret", directoryViewer) {
                treeItemTypeService.markAsTopSecretFolder(it, true)
                treeItemTypeService.updateState()
                refreshDirectories()
            },
            createMenuItem("Secret", directoryViewer) {
                treeItemTypeService.markAsSecretFolder(it, true)
                treeItemTypeService.updateState()
                refreshDirectories()
            },
            createMenuItem("Non-secret", directoryViewer) {
                treeItemTypeService.markAsNonSecretFolder(it, true)
                treeItemTypeService.updateState()
                refreshDirectories()
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