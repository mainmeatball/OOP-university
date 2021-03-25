package org.leti.lab5.component.table

interface NamePropertiesAware {
    val name: String
    val properties: MutableMap<String, Boolean>
}