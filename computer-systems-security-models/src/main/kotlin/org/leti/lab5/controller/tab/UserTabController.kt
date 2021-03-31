package org.leti.lab5.controller.tab

import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.User
import org.leti.lab5.service.UserRoleStateService


class UserTabController {

    private val stateService = UserRoleStateService

    @FXML
    lateinit var userTable: TableView<User>

    @FXML
    lateinit var userName: TextField

    @FXML
    fun initialize() {
        println("Start initializing User Tab Controller")
        stateService.initializeTable(userTable, USER_TABLE_NAME)
        stateService.addUserItems(userTable, *InMemoryStorage.userSet.toTypedArray())
        userTable.isEditable = true
        println("User Tab Controller is initialized")
    }

    @FXML
    fun createNewUser() {
        val newUserName = userName.text ?: return
        userName.clear()
        stateService.addUserItemsAndSaveState(userTable, User(newUserName))
    }

    companion object {
        private const val USER_TABLE_NAME = "User"
    }
}