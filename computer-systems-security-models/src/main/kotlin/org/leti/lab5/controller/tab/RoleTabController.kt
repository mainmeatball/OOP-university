package org.leti.lab5.controller.tab

import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.Role
import org.leti.lab5.service.UserRoleStateService


class RoleTabController {

    private val stateService = UserRoleStateService

    @FXML
    lateinit var roleTable: TableView<Role>

    @FXML
    lateinit var roleName: TextField

    @FXML
    fun initialize() {
        println("Start initializing Role Tab Controller")
        stateService.initializeTable(roleTable, ROLE_TABLE_NAME)
        stateService.addRoleItems(roleTable, *InMemoryStorage.roleSet.toTypedArray())
        roleTable.isEditable = true
        println("Role Tab Controller is initialized")
    }

    @FXML
    fun createNewRole() {
        val newRoleName = roleName.text ?: return
        roleName.clear()
        stateService.addRoleItemsAndSaveState(roleTable, Role(newRoleName))
    }

    companion object {
        private const val ROLE_TABLE_NAME = "Role"
    }
}