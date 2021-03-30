package org.leti.lab5.component

import javafx.scene.control.ListCell
import javafx.scene.image.ImageView

class SecurityTypeListCell : ListCell<FolderColor>() {

    override fun updateItem(item: FolderColor?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null) {
            return
        }
        graphic = ImageView(item.pictureName)
        text = item.toString().toLowerCase().capitalize()
    }
}