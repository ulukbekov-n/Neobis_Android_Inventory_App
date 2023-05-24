package com.example.neobis_android_inventory_app.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.neobis_android_inventory_app.model.Product
@Dao
interface ProductArchiveDao {
    @Insert
    suspend fun addProduct(product: Product)
}