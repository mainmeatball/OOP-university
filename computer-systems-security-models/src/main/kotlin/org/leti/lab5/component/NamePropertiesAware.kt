package org.leti.lab5.component

interface NamePropertiesAware {
    val name: String
    val properties: MutableMap<String, Boolean>
}