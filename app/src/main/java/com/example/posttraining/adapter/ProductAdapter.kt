package com.example.posttraining.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.posttraining.R
import com.example.posttraining.model.Product

class ProductAdapter (private var productList: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var nameTv : TextView
        private lateinit var imageTv: ImageView

        init {
            nameTv = itemView.findViewById(R.id.productNameTv)
            imageTv = itemView.findViewById(R.id.productImage)
        }

        fun bind(product: Product){
            nameTv.text = product.productName
            imageTv.setImageBitmap(product.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cart, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

}

