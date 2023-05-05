package com.example.neobis_android_inventory_app.data

import Converters
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.neobis_android_inventory_app.data.Converters;


@Entity(tableName = "product_table")
@TypeConverters(Converters::class)
data class Product(

    @PrimaryKey(autoGenerate =true)
    val id: Int,
//  val image Bitmap
//  typeConverter
    //bottom sheet
    //alert dialog
    val modelName: String,
    val Cost: String,
    val companyName: String,
    val Quantity: String,
    val image: Bitmap

//    val productPhoto: Bitmap



)
