package com.zeepy.zeepyforandroid.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.zeepy.zeepyforandroid.loadUrl
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.IOException
import okio.source
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object FileConverter {



    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }

    fun Uri.asBitmap(contentResolver: ContentResolver): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, this))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, this)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun convertUrlToBitmap(urlString: String): Bitmap? {
        val bitmap: Bitmap? = null
        try {
            val url = URL(urlString)
            val stream = url.openStream()
            return BitmapFactory.decodeStream(stream)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

}