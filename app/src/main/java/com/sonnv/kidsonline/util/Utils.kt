package com.sonnv.kidsonline.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


fun String.convertToMD5(): String {
    val MD5 = "MD5"
    try {
        // Create MD5 Hash
        val digest: MessageDigest = MessageDigest
            .getInstance(MD5)
        digest.update(this.toByteArray())
        val messageDigest: ByteArray = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

fun getChecksum(data: List<Pair<String, String>>): String {
    val checkSum = StringBuilder(CHECKSUM_CODE)
    data.forEach {
        checkSum.append("${it.first}${it.second}")
    }
    return checkSum.toString().convertToMD5()
}

fun getCurrentDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}

fun getYMD(value: Int): Int {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    return calendar.get(value)
}

fun getRelationShip(key: Int): String {
    return when (key) {
        RelationShip.ME.key -> RelationShip.ME.value
        RelationShip.BO.key -> RelationShip.BO.value
        RelationShip.ONG.key -> RelationShip.ONG.value
        RelationShip.BA.key -> RelationShip.BA.value
        RelationShip.CO.key -> RelationShip.CO.value
        RelationShip.DI.key -> RelationShip.DI.value
        RelationShip.CHU.key -> RelationShip.CHU.value
        RelationShip.BAC.key -> RelationShip.BA.value
        else -> "-"
    }
}

fun getPathFromUri(context: Context, uri: Uri): String? {
    // DocumentProvider
    if (DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {
            getFileName(context, uri)?.let { fileName ->
                return Environment.getExternalStorageDirectory()
                    .toString() + "/Download/" + fileName
            }

            var documentId = DocumentsContract.getDocumentId(uri)
            if (documentId.startsWith("raw:")) {
                documentId = documentId.replace("raw:", "")
                if (File(documentId).exists()) {
                    return documentId
                }
            }

            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                documentId.toLong()
            )
            return getDataColumn(
                context,
                contentUri,
                null,
                null
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            return getDataColumn(
                context,
                contentUri,
                selection,
                selectionArgs
            )
        }// MediaProvider
        // DownloadsProvider
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        // Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context,
            uri,
            null,
            null
        )
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }// File
    // MediaStore (and general)
    return null
}

// returns whether the Uri authority is ExternalStorageProvider.
private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

// returns whether the Uri authority is DownloadsProvider.
private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

// returns whether the Uri authority is MediaProvider.
private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

// returns whether the Uri authority is Google Photos.
private fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}

private fun getDataColumn(
    context: Context,
    uri: Uri?,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor =
            context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

private fun getFileName(
    context: Context,
    uri: Uri
): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

    try {
        cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

