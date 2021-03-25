package org.leti.lab5.component

import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.util.Callback

class CheckBoxCellFactory<S, T> : Callback<TableColumn<S, T>, TableCell<S, T>> {
    override fun call(param: TableColumn<S, T>?) = CheckBoxTableCell<S, T>()
}