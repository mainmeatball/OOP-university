package org.leti.lab5.component.table

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Role
@JsonCreator constructor(
    @JsonProperty("name") override val name: String,
    @JsonProperty("properties") override val properties: MutableMap<String, Boolean> = mutableMapOf()
) : NamePropertiesAware