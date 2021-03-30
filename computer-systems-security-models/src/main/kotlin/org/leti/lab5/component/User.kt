package org.leti.lab5.component

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class User
@JsonCreator constructor(
    @JsonProperty("name") override val name: String,
    @JsonProperty("properties") override val properties: MutableMap<String, Boolean> = mutableMapOf()
) : NamePropertiesAware {

    override fun toString(): String {
        return name
    }
}