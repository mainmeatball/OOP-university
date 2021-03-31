package org.leti.lab5.service

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.paint.Color
import javafx.util.Callback
import org.leti.lab4.service.ApplicationStateService
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.*
import org.leti.lab5.enums.UserActionType

private const val USER_TABLE_NAME = "User"
private const val ROLE_TABLE_NAME = "Role"
private const val DELETE_BUTTON_NAME = "Delete"

object UserRoleStateService {

    private val appStateService = ApplicationStateService
    private val tableMap = mutableMapOf<String, TableView<out NamePropertiesAware>>()

    var updateUserCallback: (User, UserActionType) -> Unit = { _, _ -> }
    var updateSecurityTypeCallback: (UserActionType, Boolean, Array<out SecurityType>) -> Unit = { _, _, _ -> }
    var log: (String, Color) -> Unit = { _, _ -> }

    fun addUserItems(table: TableView<User>, vararg items: User) {
        for (item in items) {
            if (table.items.any { it.name == item.name }) {
                log("User ${item.name} already exists", Color.RED)
                continue
            }
            table.items.add(item)
            InMemoryStorage.userSet += item
            updateUserCallback(item, UserActionType.ADD)
        }
    }

    fun addUserItemsAndSaveState(table: TableView<User>, vararg items: User) {
        addUserItems(table, *items)
        saveState()
    }

    fun addRoleItems(table: TableView<Role>, vararg items: Role) {
        for (item in items) {
            if (table.items.any { it.name == item.name }) {
                log("Role ${item.name} already exists", Color.RED)
                continue
            }
            table.items.add(item)
            InMemoryStorage.roleSet += item
            addNewColumn<User>(USER_TABLE_NAME, item.name)
        }
    }

    fun addRoleItemsAndSaveState(table: TableView<Role>, vararg items: Role) {
        addRoleItems(table, *items)
        saveState()
    }

    fun addSecurityTypes(table: TableView<SecurityType>, vararg items: SecurityType) {
        for (item in items) {
            if (table.items.any { it.name == item.name }) {
                log("Security type ${item.name} already exists", Color.RED)
                continue
            }
            table.items.add(item)
            InMemoryStorage.securityTypeSet += item
            addNewColumn<Role>(ROLE_TABLE_NAME, item.name)
        }
        updateSecurityTypeCallback(UserActionType.ADD, true, items)
    }

    fun addSecurityTypeAndSaveState(table: TableView<SecurityType>, vararg items: SecurityType) {
        addSecurityTypes(table, *items)
        saveState()
    }

    fun <T : NamePropertiesAware> initializeTable(table: TableView<T>, tableName: String) {
        val columns = table.columns
        val deleteColumn = TableColumn<T, Button>(DELETE_BUTTON_NAME).apply {
            cellValueFactory = Callback {
                val entity = it.value
                val button = Button(DELETE_BUTTON_NAME)
                button.setOnAction {
                    deleteItem(table, entity)
                }
                SimpleObjectProperty(button)
            }
        }
        columns += deleteColumn
        val nameColumn = TableColumn<T, String>(tableName).apply {
            cellValueFactory = PropertyValueFactory("name")
        }
        columns += nameColumn
        tableMap[tableName] = table
    }

    fun <T : NamePropertiesAware> addNewColumn(tableName: String, name: String) {
        val newColumn = TableColumn<T, CheckBox>(name).apply {
            cellValueFactory = Callback {
                val entity = it.value
                val checkBox = CheckBox().apply {
                    selectedProperty().value = entity.properties[name] ?: false
                    selectedProperty().addListener { _, _, newVal ->
                        entity.properties[name] = newVal
                        saveState()
                    }
                }
                SimpleObjectProperty(checkBox)
            }
        }
        @Suppress("UNCHECKED_CAST")
        val columns = tableMap[tableName]!!.columns as ObservableList<TableColumn<T, *>>
        columns.add(newColumn)
    }

    private fun <T : NamePropertiesAware> removeColumn(tableName: String, name: String) {
        @Suppress("UNCHECKED_CAST")
        val columns = tableMap[tableName]!!.columns as ObservableList<TableColumn<T, *>>
        columns.removeIf { it.text == name }
    }

    private fun <T : NamePropertiesAware> deleteItem(table: TableView<T>, item: T) {
        table.items.remove(item)
        if (item is User) {
            InMemoryStorage.userSet.remove(item)
            updateUserCallback(item, UserActionType.REMOVE)
        }
        if (item is Role) {
            InMemoryStorage.roleSet.remove(item)
            removeColumn<User>(USER_TABLE_NAME, item.name)
        }
        saveState()
    }

    fun deleteSecurityTypes(table: TableView<SecurityType>, withRefresh: Boolean, vararg items: SecurityType) {
        for (item in items) {
            table.items.remove(item)
            InMemoryStorage.securityTypeSet.remove(item)
            removeColumn<Role>(ROLE_TABLE_NAME, item.name)
        }
        updateSecurityTypeCallback(UserActionType.REMOVE, withRefresh, items)
        saveState()
    }

    fun saveState() {
        appStateService.saveRolesUsersState(UserRole(InMemoryStorage.userSet, InMemoryStorage.roleSet, InMemoryStorage.securityTypeSet))
    }
}