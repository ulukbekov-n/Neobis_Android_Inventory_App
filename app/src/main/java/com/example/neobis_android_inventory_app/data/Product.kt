package com.example.neobis_android_inventory_app.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "product_table")
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
//    val productPhoto: Bitmap



)
