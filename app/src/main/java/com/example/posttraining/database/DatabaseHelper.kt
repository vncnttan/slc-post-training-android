package com.example.posttraining.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.posttraining.model.Product
import java.io.ByteArrayOutputStream

class DatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, "trainingDB", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val queryProduct = "CREATE TABLE Product(" +
                "ProductID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductName TEXT, " +
                "ProductImage BLOB, " +
                "ProductType TEXT" +
                ")"

        db?.execSQL(queryProduct)
    }

    fun insertProduct(product: Product){
        val db = writableDatabase

        val values = ContentValues().apply {
            put("ProductName", product.productName)
            put("ProductType", product.productType)
            put("ProductImage", bitmapToByteArray(product.productImage))
        }

        db.insert("Product", null, values)

        db.close()
    }

    fun getProducts() : ArrayList<Product>{
        val products = ArrayList<Product>()

        val db = readableDatabase
        val query = "SELECT * FROM Product"
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()

        if(cursor.count > 0){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("ProductID"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"))
                val image = cursor.getBlob(cursor.getColumnIndexOrThrow("ProductImage"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow("ProductType"))
                products.add(Product(id, name, ByteArrayToBitmap(image), type))
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        db.close()

        return products
    }

    fun ByteArrayToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Product")
        onCreate(db)
    }


}