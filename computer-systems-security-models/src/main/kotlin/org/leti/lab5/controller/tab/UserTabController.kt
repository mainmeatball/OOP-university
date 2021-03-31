package org.leti.lab5.controller.tab

import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import org.leti.lab4.service.LoggerService
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.User
import org.leti.lab5.dao.UserDao
import org.leti.lab5.service.UserRoleStateService


class UserTabController {

    private val stateService = UserRoleStateService

    private val userDao = UserDao()

    @FXML
    lateinit var userTable: TableView<User>

    @FXML
    lateinit var userName: TextField

    @FXML
    fun initialize() {
        stateService.initializeTable(userTable, USER_TABLE_NAME)
        stateService.addUserItems(userTable, *userDao.findAll().toTypedArray())
        userTable.isEditable = true
    }

    @FXML
    fun createNewUser() {
        val newUserName = userName.text ?: return
        if (newUserName.isBlank()) {
            LoggerService.error("Enter valid user name")
        }
        userName.clear()
        stateService.addUserItemsAndSaveState(userTable, User(newUserName))
        LoggerService.success("User $newUserName was successfully created")
    }

    companion object {
        private const val USER_TABLE_NAME = "User"
    }
}