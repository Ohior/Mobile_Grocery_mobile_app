package com.example.groceryapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickInterface{

    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var  list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)

        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list, this)

        //don't forget to set layout manager for recycler view
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = groceryRVAdapter

        val groceryRepository = GroceryRepository(GroceryDataBase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })
        
        addFAB.setOnClickListener { view ->

        }
    }
    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val  cancelBtn: Button = dialog.findViewById(R.id.idBtnCancel)
        val  addBtn: Button = dialog.findViewById(R.id.idBtnAdd)
        val  itemEdt: EditText = dialog.findViewById(R.id.idEditItemName)
        val  itemPriceEdt: EditText = dialog.findViewById(R.id.idEditItemPrice)
        val  itemQuantityEdt: EditText = dialog.findViewById(R.id.idEditItemQuantity)
        
        cancelBtn.setOnClickListener { view ->
            dialog.dismiss()
        }
        addBtn.setOnClickListener { view ->
            val itemName = itemEdt.text.toString()
            val itemPrice = itemPriceEdt.text.toString()
            val itemQuantity = itemQuantityEdt.text.toString()

            val qty = itemQuantity.toInt()
            val pr = itemPrice.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()){
                val items = GroceryItems(itemName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "Item inserted", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext, "Please enter all the data...", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
    }
}