package com.example.neobis_android_inventory_app.data
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageConverter {
    fun fromByteArray(bytes: ByteArray?): Bitmap? {
        return if (bytes == null) {
            null
        } else {

            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }
    @TypeConverter
    fun toByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap == null) {
            null
        } else {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.toByteArray()
        }
    }
}