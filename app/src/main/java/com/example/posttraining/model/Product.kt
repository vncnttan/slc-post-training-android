package com.example.posttraining.model

import android.graphics.Bitmap

data class Product (
    val productID: Int,
    val productName: String,
    val productImage: Bitmap,
    val productType: String
)
