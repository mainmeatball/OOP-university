package org.leti.lab4.service

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.leti.lab4.component.TreeItemType
import org.leti.lab4.component.TypeAwareTreeItem
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.SecurityType

object TreeItemTypeMarkerService {

    private val appStateService = ApplicationStateService

    private val fileIcon = Image(javaClass.classLoader.getResourceAsStream("images/file-icon.png"))

    init {
        InMemoryStorage.dirSecurityTypeCache.putAll(appStateService.fetchState())
    }

    fun markAsFile(treeItem: TypeAwareTreeItem) {
        if (treeItem.type != TreeItemType.FILE) {
            return
        }
        treeItem.graphic = ImageView(fileIcon)
    }

    fun markAsNonSecret(
        treeItem: TypeAwareTreeItem,
        availableTypes: Collection<String>
    ) {
        if (InMemoryStorage.hasFolder(treeItem.absolutePath)) {
            val cachedSecurityType = InMemoryStorage.getFolderSecurityType(treeItem.absolutePath)
            if (cachedSecurityType.name !in availableTypes) {
                markFolder(treeItem, SecurityType.NON_SECRET)
                return
            }
            markFolder(treeItem, cachedSecurityType)
            return
        }
        markFolder(treeItem, SecurityType.NON_SECRET)
    }

    fun markFolder(treeItem: TypeAwareTreeItem, securityFolderType: SecurityType) {
        if (treeItem.type != TreeItemType.FOLDER) {
            return
        }
        treeItem.security = securityFolderType
        treeItem.graphic = ImageView(securityFolderType.color.pictureName)
        InMemoryStorage.setFolderSecurityType(treeItem.absolutePath, treeItem.security)
    }

    fun resolveSecurityType(path: String): SecurityType {
        return InMemoryStorage.getSafeFolderSecurityType(path)
    }

    fun updateState() {
        appStateService.saveState(InMemoryStorage.dirSecurityTypeCache)
    }
}