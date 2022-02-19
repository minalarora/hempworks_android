package com.hemp.works.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.FileOutputStream

object FileUtils {
    const val ANY_FILES = "*/*"
    const val SERVER_FILE_TYPE = "file/*"
    const val JPG_FILE = ".jpg"
    const val PDF_FILE = ".pdf"
    const val SERVER_IMAGE_KEY_NAME = "image"
    const val SERVER_PDF_KEY_NAME = "pdf"
    const val SERVER_JSON_KEY_NAME = "json"
    const val REQUEST_MEDIA_TYPE = "text/plain"
    const val MIME_TYPES = "image/jpeg"
    const val MAX_FILE_SIZE = 1048576 * 10
    const val IMAGE_TYPE = "image"
    const val PDF_TYPE = "pdf"

    /**
     * This method will check permission
     */
    fun checkPermission(permissions: Array<String>, activity: FragmentActivity): Boolean {
        var granted = true
        for (per in permissions) {
            if (!permissionGranted(per, activity)) {
                granted = false
                break
            }
        }
        if (granted) {
            return true
        }
        return false
    }

    /**
     * This method is responsible to check if permission is granted or not
     */
    private fun permissionGranted(permission: String, activity: FragmentActivity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getFileFromUri(
        context: Context,
        uri: Uri?,
        directoryName: String,
        directory: String
    ): File? {
        try {
            val `in` = context.contentResolver.openInputStream(uri!!)
            val directory =
                File(context.getExternalFilesDir(directory)?.absolutePath, directoryName)
            directory.mkdir()
            var file = File(directory, getFileName(context, uri))
            if (file.exists()) {
                //TODO: add some logic to check mkdirs response
                file.delete()
                file = File(directory, getFileName(context, uri))
            }
            val out = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (`in`!!.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            out.flush()
            out.close()
            `in`.close()
            return file
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    @SuppressLint("Range")
    fun getFileName(context: Context, _uri: Uri?): String? {
        var filePath: String? = ""
        if (_uri != null && "content" == _uri.scheme) {
            //Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
            //context.revokeUriPermission(_uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(
                    _uri,
                    null, null, null, null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    filePath = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } catch (e: SecurityException) {
                //if file open with third party application
                if (_uri.toString().contains("/storage/emulated/0")) {
                    filePath = "/storage/emulated/0" + _uri.toString()
                        .split("/storage/emulated/0".toRegex()).toTypedArray()[1]
                }
            } finally {
                cursor?.close()
            }
        } else {
            filePath = _uri!!.path
        }
        return filePath
    }

    fun getFileSize(context: Context, _uri: Uri?): Long {
        return if (_uri != null && "content" == _uri.scheme) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(
                        _uri,
                        null, null, null, null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (!cursor.isNull(sizeIndex)) {
                        cursor.getLong(sizeIndex)
                    } else {
                        0
                    }
                } else {
                    0
                }
            } catch (e: SecurityException) {
                //if file open with third party application
                0
            } finally {
                cursor?.close()
            } as Long
        } else {
            0
        }
    }

    fun getExtension(fileName: String?): String {
        var ch: Char? = null
        var len: Int = 0
        if (fileName == null || fileName.length.also {
                len = it
            } == 0 || fileName[len - 1].also {
                ch = it
            } == '/' || ch == '\\' || //in the case of a directory
            ch == '.') //in the case of . or ..
        {
            return ""
        }
        val dotInd = fileName.lastIndexOf('.')
        val sepInd = fileName.lastIndexOf('/').coerceAtLeast(fileName.lastIndexOf('\\'))
        return if (dotInd <= sepInd) {
            ""
        } else {
            fileName.substring(dotInd + 1).toLowerCase()
        }
    }

    fun deleteFiles(
        context: Context,
        sampleFile: String,
        filePath: String,
        envDirectory: String
    ) {
        try {
            val directory = File(context.getExternalFilesDir(envDirectory), filePath)
            if (directory.isDirectory && directory.exists()) {
                val files = directory.listFiles()
                for (file in files) {
                    if (sampleFile.contains(file.name)) {
                        file.delete()
                        break
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}