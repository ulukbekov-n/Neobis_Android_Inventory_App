package com.example.neobis_android_inventory_app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neobis_android_inventory_app.model.Product

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

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product_table WHERE modelName LIKE :query OR companyName LIKE :query")
    fun searchProducts(query: String): LiveData<List<Product>>



}