package com.example.neobis_android_inventory_app.data

import android.content.Context
import androidx.room.*
import com.example.neobis_android_inventory_app.model.Product

//@TypeConverter
//fun fromByteArray(bytes: ByteArray?): Bitmap? {
//    return if (bytes == null) {
//        null
//    } else {
//        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//    }
//}
//
//@TypeConverter
//fun toByteArray(bitmap: Bitmap?): ByteArray? {
//    return if (bitmap == null) {
//        null
//    } else {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        stream.toByteArray()
//    }
//}

@Database(
    entities = [Product::class], version = 2
)
@TypeConverters(ImageConverter::class)

abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, ProductDatabase::class.java, "product_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}



