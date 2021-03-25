package org.leti.lab5.controller

import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import org.leti.lab4.controller.MacController
import org.leti.lab5.component.table.User
import org.leti.lab5.service.UserAwareDirectoryInitializationService
import org.leti.lab5.service.UserRoleStateService


class MainController : MacController() {

    private val stateService = UserRoleStateService

    override val directoryInitializationService = UserAwareDirectoryInitializationService()

    @FXML
    lateinit var currentUser: ComboBox<User>

    override fun initialize() {
        super.initialize()
        currentUser.items.addAll(stateService.userList)
        stateService.updateUserCallback = { user, action ->
            action.doAction(currentUser.items, user)
        }
        stateService.log = { message, color -> log(message, color) }
    }

    @FXML
    fun updateView() {
        val user = currentUser.selectionModel.selectedItem ?: return
        directoryInitializationService.currentUser = user
        refreshDirectories()
    }
}