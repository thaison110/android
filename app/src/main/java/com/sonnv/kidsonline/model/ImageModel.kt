package com.sonnv.kidsonline.model

import android.content.Context
import android.net.Uri
import java.io.Serializable

data class ImageModel(
    val path: String,
    val uri: Uri,
    var isSelected: Boolean = false,
    var number: Int = 0
): Serializable {
    fun getPathFromUri(context: Context?): String? {
        return if (context == null) "" else com.sonnv.kidsonline.util.getPathFromUri(context, uri)
    }
}
