package org.leti.lab4.service

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.leti.lab4.component.SecurityFolderType
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.component.TypeAwareTreeItem

object TreeItemTypeMarkerService {

    private val appStateService = ApplicationStateService

    private val fileIcon = Image(javaClass.classLoader.getResourceAsStream("images/file-icon.png"))
    private val blueFolderIcon = Image(javaClass.classLoader.getResourceAsStream("images/blue-folder-icon.png"))
    private val redFolderIcon = Image(javaClass.classLoader.getResourceAsStream("images/red-folder-icon.png"))
    private val greenFolderIcon = Image(javaClass.classLoader.getResourceAsStream("images/green-folder-icon.png"))

    private val securityCache = mutableMapOf<String, SecurityFolderType>()

    private val securityIconStrategy = mapOf(
        SecurityFolderType.NON_SECRET to blueFolderIcon,
        SecurityFolderType.SECRET to greenFolderIcon,
        SecurityFolderType.TOP_SECRET to redFolderIcon,
    )

    init {
        securityCache.putAll(appStateService.fetchState())
    }

    fun markAsFile(treeItem: TypeAwareTreeItem) {
        if (treeItem.type != TreeItemType.FILE) {
            return
        }
        treeItem.graphic = ImageView(fileIcon)
    }

    fun markAsTopSecretFolder(treeItem: TypeAwareTreeItem, withForce: Boolean = false) {
        if (securityCache.containsKey(treeItem.absolutePath) && !withForce) {
            markFolder(treeItem, securityCache[treeItem.absolutePath]!!)
            return
        }
        markFolder(treeItem, SecurityFolderType.TOP_SECRET)
    }

    fun markAsSecretFolder(treeItem: TypeAwareTreeItem, withForce: Boolean = false) {
        if (securityCache.containsKey(treeItem.absolutePath) && !withForce) {
            markFolder(treeItem, securityCache[treeItem.absolutePath]!!)
            return
        }
        markFolder(treeItem, SecurityFolderType.SECRET)
    }

    fun markAsNonSecretFolder(treeItem: TypeAwareTreeItem, withForce: Boolean = false) {
        if (securityCache.containsKey(treeItem.absolutePath) && !withForce) {
            markFolder(treeItem, securityCache[treeItem.absolutePath]!!)
            return
        }
        markFolder(treeItem, SecurityFolderType.NON_SECRET)
    }

    fun markFolder(treeItem: TypeAwareTreeItem, securityFolderType: SecurityFolderType) {
        if (treeItem.type != TreeItemType.FOLDER) {
            return
        }
        treeItem.security = securityFolderType
        treeItem.graphic = ImageView(securityIconStrategy[securityFolderType])
        securityCache[treeItem.absolutePath] = treeItem.security
    }

    fun resolveSecurityType(path: String): SecurityFolderType {
        return securityCache[path] ?: SecurityFolderType.NON_SECRET
    }

    fun updateState() {
        appStateService.saveState(securityCache)
    }

    fun updateCache(path: String, securityType: SecurityFolderType) {
        securityCache[path] = securityType
    }
}