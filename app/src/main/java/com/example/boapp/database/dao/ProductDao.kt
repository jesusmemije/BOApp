package com.example.boapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.boapp.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity): Long

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("DELETE FROM product_table")
    suspend fun deleteProducts()
}