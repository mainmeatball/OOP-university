package org.leti.lab5.controller.tab

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.paint.Color
import javafx.util.Callback
import org.leti.lab4.service.ApplicationStateService
import org.leti.lab4.service.LoggerService
import org.leti.lab4.storage.InMemoryStorage
import org.leti.lab5.component.FolderColor
import org.leti.lab5.component.SecurityType
import org.leti.lab5.component.SecurityTypeListCell
import org.leti.lab5.service.UserRoleStateService


class SecurityTabController {

    private val stateService = UserRoleStateService

    @FXML
    lateinit var securityTypeTable: TableView<SecurityType>

    @FXML
    lateinit var securityName: TextField

    @FXML
    fun initialize() {
        println("Start initializing Security Tab Controller")
        val columns = securityTypeTable.columns
        val deleteColumn = TableColumn<SecurityType, Button>("Delete").apply {
            cellValueFactory = Callback {
                val entity = it.value
                if (entity.name == SecurityType.NON_SECRET.name) {
                    return@Callback SimpleObjectProperty()
                }
                val button = Button("Delete")
                button.setOnAction {
                    stateService.deleteSecurityTypes(securityTypeTable, true, entity)
                }
                SimpleObjectProperty(button)
            }
        }
        columns += deleteColumn
        val nameColumn = TableColumn<SecurityType, String>("SecurityType").apply {
            cellValueFactory = PropertyValueFactory("name")
        }
        columns += nameColumn
        securityTypeTable.isEditable = true
        val newColumn = TableColumn<SecurityType, ComboBox<FolderColor>>("Color").apply {
            setCellValueFactory {
                val entity = it.value
                val comboBox = ComboBox(FXCollections.observableArrayList(*FolderColor.values())).apply {
                    value = entity.color
                    buttonCell = SecurityTypeListCell()
                    setCellFactory {
                        SecurityTypeListCell()
                    }
                    selectionModel.selectedItemProperty().addListener { _, _, newVal ->
                        entity.color = newVal
                        println("chosen $newVal")
                    }
                }
                SimpleObjectProperty(comboBox)
            }
        }
        columns.add(newColumn)
        val priorityColumn = TableColumn<SecurityType, TextField>("Priority").apply {
            cellValueFactory = Callback {
                val entity = it.value
                val textField = TextField().apply {
                    text = entity.priority.toString()
                    textProperty().addListener { _, _, newValue ->
                        println("New priority chosen $newValue")
                        if (newValue.isNotEmpty()) {
                            entity.priority = newValue.toInt()
                        }
                    }
                }
                SimpleObjectProperty(textField)
            }
        }
        columns += priorityColumn
        stateService.addSecurityTypeAndSaveState(securityTypeTable, *InMemoryStorage.securityTypeSet.sortedByDescending { it.priority }.toTypedArray())
        println("Security Tab Controller is initialized")
    }


    @FXML
    fun createNewSecurityType() {
        val newSecurityTypeName = securityName.text
        if (newSecurityTypeName.isNullOrBlank()) {
            LoggerService.error("Enter valid security type")
        }
        securityName.clear()
        val newColor = uniqueColors().first()
        stateService.addSecurityTypeAndSaveState(securityTypeTable, SecurityType(newSecurityTypeName, 0, newColor))
    }

    @FXML
    fun saveState() {
        val validated = validateItems(securityTypeTable.items)
        if (!validated) {
            return
        }
        val items = securityTypeTable.items
            .sortedByDescending{ it.priority }
            .toMutableList().toTypedArray()
        stateService.deleteSecurityTypes(securityTypeTable, false, *items)
        stateService.addSecurityTypeAndSaveState(securityTypeTable, *items)
    }

    private fun validateItems(items: List<SecurityType>): Boolean {
        val hasDuplicatedColor = items.groupingBy { it.color }.eachCount().any { it.value > 1 }
        if (hasDuplicatedColor) {
            LoggerService.error("Please, choose another folder color")
            return false
        }
        val hasDuplicatedPriority = items.groupingBy { it.priority }.eachCount().any { it.value > 1 }
        if (hasDuplicatedPriority) {
            LoggerService.error("Please, choose another security priority")
            return false
        }
        LoggerService.log("Security type state saved", Color.GREEN)
        return true
    }

    private fun uniqueColors(): List<FolderColor> {
        return FolderColor.values().filter { it !in securityTypeTable.items.map(SecurityType::color) }
    }
}