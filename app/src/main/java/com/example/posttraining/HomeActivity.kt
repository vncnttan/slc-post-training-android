package com.example.posttraining

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.posttraining.adapter.ProductAdapter
import com.example.posttraining.database.DatabaseHelper
import com.example.posttraining.databinding.ActivityHomeBinding
import com.example.posttraining.model.Product

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var usernameTv: TextView
    private lateinit var productRv : RecyclerView
    private lateinit var productAdapter : ProductAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameTv = binding.usernameTv

        usernameTv.text = intent.getStringExtra("usernameExtra")


        productRv = binding.productRv

        binding.goToInsertBtn.setOnClickListener(){
            val intent = Intent(this, activity_insert::class.java)
            startActivity(intent)
        }
        setUpRecycler()
    }

    private fun setUpRecycler(){
//        val imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.ric)
//        val imageBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.gear)
//        val imageBitmap3 = BitmapFactory.decodeResource(resources, R.drawable.sword)
//
//        val arrayProduct = ArrayList<Product>()
//        arrayProduct.add(Product(1, "Product 1", imageBitmap, "Phone"))
//        arrayProduct.add(Product(2, "Product 2", imageBitmap2, "Tools"))
//        arrayProduct.add(Product(3, "Product 3", imageBitmap3, "Tools"))
        val arrayProduct = databaseHelper.getProducts()
        productAdapter = ProductAdapter(arrayProduct)
        productRv.layoutManager = LinearLayoutManager(this)
        productRv.adapter = productAdapter
    }
}