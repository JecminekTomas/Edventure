package com.example.tutorme.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Ze StackOverflow
 */
object FileUtils {
    /**
     * Vytvoří prázdný soubor, se kterým budeme následně pracovat.
     * @return nový prázdný soubor
     * @throws IOException vyjímka
     */
    @Throws(IOException::class)
    fun createImageFile(context: Context): File { // timestamp of the image. Because of the timestamp each image name will be unique.
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.ENGLISH).format(Date())
        val imageFileName = "photo_" + timeStamp + "_"
        // imageFileName slouží k vytvoření unikátního jména souboru.
        val storageDirString = context.filesDir.toString()
        val storageDir = File(storageDirString)
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

    /**
     * Kopíruje zdrojové soubory
     * @param src zdrojový soubor
     * @param dst cílový sobor
     * @throws IOException exception
     */
    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        val input: InputStream = FileInputStream(src)
        input.use { input ->
            val output: OutputStream = FileOutputStream(dst)
            output.use { out ->
                // Převede bajty z vstupu na výstup
                val buf = ByteArray(1024)
                var len: Int
                while (input.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }
    /*
      ************************************
      This part forward was put together based on stack overflow discussions:
      https://stackoverflow.com/questions/2789276/android-get-real-path-by-uri-getpath
      https://stackoverflow.com/questions/17546101/get-real-path-for-uri-android
      ************************************
     */
    /**
     * Returns real path of the files
     * @param context context
     * @param uri uri
     * @return Returns real path of the files
     */
    fun getRealPath(context: Context, uri: Uri): String? {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(
                    context,
                    contentUri,
                    null,
                    null
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(
                    context,
                    contentUri,
                    selection,
                    selectionArgs
                )
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri
            .authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun isGoogleDriveUri(uri: Uri): Boolean {
        return "com.google.android.apps.docs.storage" == uri.authority
    }
}