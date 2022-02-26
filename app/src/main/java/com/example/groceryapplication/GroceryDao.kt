package com.example.groceryapplication

import androidx.lifecycle.LiveData
import androidx.room.*

//data access object
@Dao
interface GroceryDao {
    // create method to be use
    // first we one for inserting data
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    // for deleting data
    @Delete
    suspend fun delete(item: GroceryItems)

    //for deleting all data
    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems() : LiveData<List<GroceryItems>>
}