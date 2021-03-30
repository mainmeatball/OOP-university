package org.leti.lab5.controller

import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.ContextMenu
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import org.leti.lab1.component.DirectoryViewer
import org.leti.lab4.component.MarkFolderMenuItem
import org.leti.lab4.controller.MacController
import org.leti.lab4.service.LoggerService
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.User
import org.leti.lab5.enums.UserActionType
import org.leti.lab5.service.UserAwareDirectoryInitializationService
import org.leti.lab5.service.UserRoleStateService


class MainController : MacController() {

    private val stateService = UserRoleStateService

    override val directoryInitializationService = UserAwareDirectoryInitializationService()

    @FXML
    lateinit var securityTypeList: VBox

    @FXML
    lateinit var currentUser: ComboBox<User>

    override fun initialize() {
        super.initialize()
        currentUser.items.addAll(stateService.userList)
        stateService.updateUserCallback = { user, action ->
            action.doAction(currentUser.items, user)
        }
        stateService.log = { message, color -> LoggerService.log(message, color) }
        addNewSecurityType(*stateService.securityList.toTypedArray())
        stateService.updateSecurityTypeCallback = { action, withRefresh, securityTypes ->
            if (action == UserActionType.ADD) {
                addNewSecurityType(*securityTypes)
            }
            if (action == UserActionType.REMOVE) {
                removeSecurityType(*securityTypes)
            }
            if (withRefresh) {
                updateContextMenu()
                refreshDirectories()
            }
        }
        println("Main Controller is initialized")
    }

    @FXML
    fun updateView() {
        val user = currentUser.selectionModel.selectedItem ?: return
        directoryInitializationService.currentUser = user
        updateContextMenu()
        refreshDirectories()
    }

    override fun createContextMenu(directoryViewer: DirectoryViewer): ContextMenu {
        return ContextMenu(
            *stateService.securityList.map {
                MarkFolderMenuItem(it.name, directoryViewer) { treeItem ->
                    treeItemTypeService.markFolder(treeItem, it)
                    treeItemTypeService.updateState()
                    refreshDirectories()
                }
            }.toTypedArray()
        )
    }

    private fun addNewSecurityType(vararg securityType: SecurityType) {
        for (it in securityType) {
            val hBox = HBox().apply {
                id = it.name
                children.add(ImageView(it.color.pictureName))
                children.add(Text(" -- ${it.name}"))
            }
            securityTypeList.children.add(hBox)
        }
    }

    private fun removeSecurityType(vararg securityType: SecurityType) {
        for (secType in securityType) {
            securityTypeList.children.removeIf { it.id == secType.name }
        }
    }
}