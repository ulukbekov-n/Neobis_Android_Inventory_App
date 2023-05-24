//package com.example.neobis_android_inventory_app.data
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.neobis_android_inventory_app.model.Product
//
//class ProductViewModelFactory (private val repository: ProductRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ProductViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}