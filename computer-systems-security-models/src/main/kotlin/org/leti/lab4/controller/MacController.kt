package org.leti.lab4.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab1.service.DirectoryInitializationService
import org.leti.lab1.service.FileService
import org.leti.lab4.component.MarkFolderMenuItem
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.dao.SecurityTypeDao
import org.leti.lab4.service.LoggerService
import org.leti.lab4.service.TreeItemTypeMarkerService
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.SecurityType
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
    lateinit var securityTypeDropdown: ComboBox<SecurityType>

    @FXML
    lateinit var newDirectoryName: TextField

    protected val treeItemTypeService = TreeItemTypeMarkerService

    protected open val directoryInitializationService = DirectoryInitializationService()

    protected val securityTypeDao = SecurityTypeDao()

    private val fileService = FileService()

    lateinit var lastChosenFilesystem: DirectoryViewer

    @FXML
    open fun initialize() {
        addDefaultSecurityTypes()
        refreshDirectories()
        updateContextMenu()
        lastChosenFilesystem = sourceDirectoryViewer
        sourceDirectoryViewer.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            lastChosenFilesystem = sourceDirectoryViewer
        }
        targetDirectoryViewer.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            lastChosenFilesystem = targetDirectoryViewer
        }
        initializeCreateNewFolderSecurityTypeDropdown()
        LoggerService.label = status
    }

    @FXML
    private fun copyFile(event: ActionEvent) {
        val selectedItem = sourceDirectoryViewer.selectedItem ?: run {
            LoggerService.log("Please, select file to copy")
            return
        }
        if (selectedItem.type == TreeItemType.FOLDER) {
            LoggerService.log("You can not copy a directory")
            return
        }
        val absoluteSourcePath = selectedItem.absolutePath
        val absoluteTargetPath = targetDirectoryViewer.currentDirectory + File.separator + sourceDirectoryViewer.selectedItem!!.value
        val fromSecurity = treeItemTypeService.resolveSecurityType(sourceDirectoryViewer.currentDirectory)
        val toSecurity = treeItemTypeService.resolveSecurityType(targetDirectoryViewer.currentDirectory)
        if (!validateSecurity(fromSecurity, toSecurity)) {
            LoggerService.error("File copy from ${fromSecurity.name} to ${toSecurity.name} folder is not allowed")
            return
        }
        LoggerService.log("File \"${sourceDirectoryViewer.selectedItem!!.value}\" successfully copied", Color.GREEN)
        fileService.copy(absoluteSourcePath, absoluteTargetPath)
        refreshTargetDirectory()
        event.consume()
    }

    @FXML
    fun createDirectory() {
        val securityType = securityTypeDropdown.value ?: return
        val newFolderName = newDirectoryName.text ?: run {
            LoggerService.log("Please, enter new directory name")
            return
        }
        try {
            val newDirPath = lastChosenFilesystem.currentDirectory + File.separator + newFolderName
            fileService.createFolder(newDirPath)
            InMemoryStorage.setFolderSecurityType(newDirPath, securityType)
            LoggerService.log("Directory $newFolderName is successfully created", Color.GREEN)
        } catch (ex: FileAlreadyExistsException) {
            LoggerService.log("Directory is already exists, fill another name")
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

    open protected fun createContextMenu(directoryViewer: DirectoryViewer): ContextMenu {
        return ContextMenu(
            *securityTypeDao.findAll().map {
                MarkFolderMenuItem(it.name, directoryViewer) { treeItem ->
                    treeItemTypeService.markFolder(treeItem, it)
                    treeItemTypeService.updateState()
                    refreshDirectories()
                }
            }.toTypedArray()
        )
    }

    fun updateContextMenu() {
        sourceDirectoryViewer.contextMenu = createContextMenu(sourceDirectoryViewer)
        targetDirectoryViewer.contextMenu = createContextMenu(targetDirectoryViewer)
    }

    private fun validateSecurity(from: SecurityType, to: SecurityType) = from.priority <= to.priority

    protected open fun initializeCreateNewFolderSecurityTypeDropdown() {
        securityTypeDropdown.items.addAll(*getDefaultSecurityTypes())
        securityTypeDropdown.value = SecurityType.NON_SECRET
    }

    protected open fun addDefaultSecurityTypes() {
        securityTypeDao.save(*getDefaultSecurityTypes())
    }

    fun getDefaultSecurityTypes() = arrayOf(SecurityType.TOP_SECRET, SecurityType.SECRET, SecurityType.NON_SECRET)
}