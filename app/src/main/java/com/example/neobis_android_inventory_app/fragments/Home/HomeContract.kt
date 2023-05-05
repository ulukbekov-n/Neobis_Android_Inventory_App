package com.example.neobis_android_inventory_app.fragments.Home

import com.example.neobis_android_inventory_app.data.Product

interface HomeContract {

    interface View {
        fun showProducts(products: List<Product>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadProducts()
    }
}