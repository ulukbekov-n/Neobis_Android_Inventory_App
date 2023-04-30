package com.example.neobis_android_inventory_app.data

import androidx.lifecycle.LiveData
import com.example.neobis_android_inventory_app.data.Product
import com.example.neobis_android_inventory_app.data.ProductDao

class ProductRepository(private val productDao: ProductDao) {
    val readAllData: LiveData<List<Product>> = productDao.readAllData()
    suspend fun addProduct(product: Product){
        productDao.addProduct(product)
    }
}