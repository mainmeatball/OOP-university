package org.leti.lab5.controller.tab

import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.leti.lab4.service.LoggerService
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.Role
import org.leti.lab5.dao.RoleDao
import org.leti.lab5.service.UserRoleStateService


class RoleTabController {

    private val stateService = UserRoleStateService

    private val roleDao = RoleDao()

    @FXML
    lateinit var roleTable: TableView<Role>

    @FXML
    lateinit var roleName: TextField

    @FXML
    fun initialize() {
        stateService.initializeTable(roleTable, ROLE_TABLE_NAME)
        stateService.addRoleItems(roleTable, *roleDao.findAll().toTypedArray())
        roleTable.isEditable = true
    }

    @FXML
    fun createNewRole() {
        val newRoleName = roleName.text ?: return
        if (newRoleName.isBlank()) {
            LoggerService.error("Enter valid role name")
        }
        roleName.clear()
        stateService.addRoleItemsAndSaveState(roleTable, Role(newRoleName))
        LoggerService.success("Role $newRoleName was successfully created")
    }

    companion object {
        private const val ROLE_TABLE_NAME = "Role"
    }
}