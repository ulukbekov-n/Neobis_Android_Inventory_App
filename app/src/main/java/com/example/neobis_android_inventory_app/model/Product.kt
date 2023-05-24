package com.example.neobis_android_inventory_app.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.example.neobis_android_inventory_app.data.ImageConverter
import kotlinx.android.parcel.Parcelize

//import androidx.room.TypeConverter

@Parcelize
@Entity(tableName = "product_table")
@TypeConverters(ImageConverter::class)
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val modelName: String,
    val Cost: String,
    val companyName: String,
    val Quantity: String,
    val image: String,

    val archived: Boolean
) : Parcelable {
}


