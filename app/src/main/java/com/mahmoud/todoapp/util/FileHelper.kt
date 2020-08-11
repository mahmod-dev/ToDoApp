package com.mahmoud.todoapp.util

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object FileHelper {

    fun toDoBasePath(context: Context): String {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

       // val destPath: String = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath

        val file = File(dir, "Todo")
        if (!file.exists()) {
            file.mkdir()
        }

        return file.path
    }

    fun createFolder(path: String, newFolder: String) {

        val file = File(path, newFolder)
        if (!file.exists()) {
            file.mkdir()
        }

    }

    fun copy(src: File, dst: File) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    fun createDirAfterPie(context: Context,fileName:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/app_name/")
            }
           resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        }
    }
}