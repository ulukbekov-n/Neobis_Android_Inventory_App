package com.example.neobis_android_inventory_app.data

import android.content.Context
import androidx.room.*

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
//    TypeConvert
}