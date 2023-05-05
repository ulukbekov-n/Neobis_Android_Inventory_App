package com.example.neobis_android_inventory_app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao

interface ProductDao {
    @Insert
    fun addProduct(product: Product)
    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table ORDER BY modelName ASC")
    fun getProductsOrderedByModelName(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table ORDER BY Cost ASC")
    fun getProductsOrderedByProductPrice(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table ORDER BY companyName ASC")
    fun getProductsOrderedByCompanyName(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table ORDER BY Quantity ASC")
    fun getProductsOrderedByQuantity(): LiveData<List<Product>>



}