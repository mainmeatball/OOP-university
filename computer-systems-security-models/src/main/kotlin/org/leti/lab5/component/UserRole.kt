package org.leti.lab5.component

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRole
@JsonCreator constructor(
    @JsonProperty("users") val users: List<User> = emptyList(),
    @JsonProperty("roles") val roles: List<Role> = emptyList(),
    @JsonProperty("security") val security: List<SecurityType> = emptyList()
)