package org.leti.lab5.component

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javafx.scene.image.Image
import java.io.Serializable


class SecurityType
@JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("priority") var priority: Int,
    @JsonProperty("color") var color: FolderColor
) : Serializable {

    override fun toString() = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as SecurityType

        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    companion object {
        val TOP_SECRET = SecurityType("Top-secret", Int.MAX_VALUE, FolderColor.RED)
        val SECRET = SecurityType("Secret", 0, FolderColor.GREEN)
        val NON_SECRET = SecurityType("Non-secret", Int.MIN_VALUE, FolderColor.BLUE)
    }
}

enum class FolderColor(
    val pictureName: Image
) {
    BLUE(Image("images/blue-folder-icon.png")),
    GREEN(Image("images/green-folder-icon.png")),
    RED(Image("images/red-folder-icon.png")),
    BLACK(Image("images/black-folder-icon.png")),
    TEAL(Image("images/teal-folder-icon.png")),
    PINK(Image("images/pink-folder-icon.png")),
    PURPLE(Image("images/purple-folder-icon.png"))
}