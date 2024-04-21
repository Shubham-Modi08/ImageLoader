package com.example.imageloader.utlitiy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageLoaderUtils {
    private lateinit var memoryCache: LruCache<String, Bitmap>
    private lateinit var diskCacheDir: File
    private const val MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50 MB
    private const val TAG = "ImageLoaderUtils"

    private val memoryCacheLock = Any()
    private val diskCacheLock = Any()

    fun initialize(context: Context) {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }

        diskCacheDir = File(context.cacheDir, "image_cache")
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs()
            Log.d(TAG, "diskCacheDirCreated")
        }
    }

    @Synchronized
    fun loadImage(imageUrl: String, imageView: ImageView, placeholder: Int) {
        val bitmap = getBitmapFromMemoryCache(imageUrl)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
            return
        }

        val task = ImageDownloadTask(imageUrl, imageView, placeholder)
        task.execute()
    }

    @Synchronized
    private fun getBitmapFromMemoryCache(key: String): Bitmap? {
        return memoryCache.get(key)
    }

    private class ImageDownloadTask(private val imageUrl: String, private val imageView: ImageView, private val placeholder: Int) :
        AsyncTask<Void, Void, Bitmap?>() {

        override fun doInBackground(vararg voids: Void): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
                Log.d(TAG, "ImageDownloadTask")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                synchronized(memoryCacheLock) {
                    memoryCache.put(imageUrl, bitmap)
                }
                saveBitmapToDiskCache(imageUrl, bitmap)
            } else {
                imageView.setImageResource(placeholder)
            }
        }
    }

    private fun saveBitmapToDiskCache(key: String, bitmap: Bitmap) {
        synchronized(diskCacheLock) {
            val file = File(diskCacheDir, key.hashCode().toString() + ".jpg")
            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.close()
                Log.d(TAG, "saveBitmapToDiskCache")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            cleanupDiskCacheIfNeeded()
        }
    }

    private fun cleanupDiskCacheIfNeeded() {
        synchronized(diskCacheLock) {
            val files = diskCacheDir.listFiles()
            var totalSize = 0L
            for (file in files!!) {
                totalSize += file.length()
            }
            if (totalSize > MAX_DISK_CACHE_SIZE) {
                files.sortedBy { it.lastModified() }.forEach { it.delete() }
            }
        }
    }
}
