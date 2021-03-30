package org.leti.lab5.enums

import org.leti.lab5.component.User

enum class UserActionType(
    val func: MutableList<User>.(User) -> Boolean
) {
    ADD(MutableList<User>::add), REMOVE(MutableList<User>::remove);

    fun doAction(users: MutableList<User>, user: User) {
        users.func(user)
    }
}