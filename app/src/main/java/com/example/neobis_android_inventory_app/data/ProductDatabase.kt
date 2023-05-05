package com.example.neobis_android_inventory_app.data

import Converters
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.*
import java.io.ByteArrayOutputStream

@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
            private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
        // ...

        @TypeConverter
        fun fromBitmap(bitmap: Bitmap?): ByteArray? {
            if (bitmap == null) {
                return null
            }
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }

        @TypeConverter
        fun toBitmap(bytes: ByteArray?): Bitmap? {
            if (bytes == null) {
                return null
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }


}