package org.leti.lab5.component

import java.io.Serializable

interface NamePropertiesAware : Serializable {
    val name: String
    val properties: MutableMap<String, Boolean>
}