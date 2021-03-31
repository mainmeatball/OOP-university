package org.leti.lab5.component

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRole
@JsonCreator constructor(
    @JsonProperty("users") val users: Collection<User> = emptyList(),
    @JsonProperty("roles") val roles: Collection<Role> = emptyList(),
    @JsonProperty("security") val security: Collection<SecurityType> = emptyList()
) : Serializable