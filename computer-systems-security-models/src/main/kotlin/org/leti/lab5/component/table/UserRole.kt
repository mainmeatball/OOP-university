package org.leti.lab5.component.table

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class UserRole
@JsonCreator constructor(
    @JsonProperty("users") val users: List<User> = emptyList(),
    @JsonProperty("roles") val roles: List<Role> = emptyList()
)