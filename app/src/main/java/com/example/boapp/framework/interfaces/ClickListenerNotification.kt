package com.example.boapp.framework.interfaces

import com.example.boapp.database.entities.ProductEntity

interface ClickListenerProduct {
    fun onItemClick(item: ProductEntity)
}