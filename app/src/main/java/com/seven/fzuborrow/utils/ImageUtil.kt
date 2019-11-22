package com.seven.fzuborrow.utils

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

fun getPath(context: Activity, uri: Uri): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.managedQuery(uri, projection, null, null, null)
    context.startManagingCursor(cursor)
    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    return cursor.getString(column_index)
}

fun convertImageUriToPath(context: Context, uri: Uri): String? {
    return convertImageUriToPath(context,Intent().setData(uri))
}

fun convertImageUriToPath(context: Context, data: Intent): String? {
    val uri = data.data
    var imagePath: String? = null
    if (DocumentsContract.isDocumentUri(context, uri)) {
        val docId = DocumentsContract.getDocumentId(uri)
        if ("com.android.providers.media.documents" == uri!!.authority) {
            val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val selection = MediaStore.Images.Media._ID + "=" + id
            imagePath =
                getImagePath(
                    context,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selection
                )
        } else if ("com.android.providers.downloads.documents" == uri.authority) {
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                java.lang.Long.valueOf(docId)
            )
            imagePath = getImagePath(context, contentUri, null)
        }
    }
    return imagePath
}

private fun getImagePath(context: Context, uri: Uri, selection: String?): String? {
    var path: String? = null
    val cursor = context.contentResolver.query(uri, null, selection, null, null)
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        }
        cursor.close()
    }
    return path
}