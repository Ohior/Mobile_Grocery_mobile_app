package com.example.groceryapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// create a database for your grocery
@Database(entities = [GroceryItems::class], version = 1)
abstract class GroceryDataBase: RoomDatabase() {
    abstract fun getGroceryDao(): GroceryDao

    companion object{
        @Volatile
        private var instance : GroceryDataBase? = null
        private var Lock = Any()

        operator fun invoke(context: Context) = synchronized(Lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                GroceryDataBase::class.java,
                "Grocery.db"
            ).build()
    }
}