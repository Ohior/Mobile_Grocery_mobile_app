package com.example.groceryapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//this class will be use in the activity to access database methods / repository
class GroceryViewModel(private var repository: GroceryRepository): ViewModel() {
    fun insert(items: GroceryItems) = GlobalScope.launch{
        repository.insert(items)
    }
    
    fun delete(items: GroceryItems) = GlobalScope.launch {
        repository.delete(items)
    }

    fun getAllGroceryItems() = repository.getAllItems()
}