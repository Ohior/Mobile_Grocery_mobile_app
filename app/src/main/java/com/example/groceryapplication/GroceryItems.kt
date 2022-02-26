package com.example.groceryapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//creating a table and give it a name
@Entity(tableName="grocery_items" )
data class GroceryItems (
    
    @ColumnInfo(name="itemName")
    var itemName: String,
    @ColumnInfo(name="itemQuantity")
    var itemQuantity: Int,
    @ColumnInfo(name="itemPrice")
    var itemPrice: Int

    ) {
    // it should auto create a number id for each item
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}