package com.example.boapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boapp.database.dao.ProductDao
import com.example.boapp.database.entities.ProductEntity

@Database(entities = [
    ProductEntity::class
], version = 2)
abstract class BOAppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: BOAppDatabase? = null

        fun getInstance(context: Context): BOAppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BOAppDatabase::class.java,
                        "BOAppDatabase"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}