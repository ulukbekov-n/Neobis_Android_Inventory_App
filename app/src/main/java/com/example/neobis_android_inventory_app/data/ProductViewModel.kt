package com.example.neobis_android_inventory_app.data

import android.app.Application
import androidx.lifecycle.*
import com.example.neobis_android_inventory_app.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors



class ProductViewModel (application: Application):AndroidViewModel(application)
{

    val readAllData:LiveData<List<Product>>
    private val repository: ProductRepository

    init{
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        readAllData = repository.readAllData
    }
    fun addProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            repository.addProduct(product)
        }
    }
    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }
    fun searchProducts(query: String): LiveData<List<Product>> {
        return repository.searchProducts(query)
    }

}
