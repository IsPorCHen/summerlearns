/*package com.bignerdranch.android.fuck3.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fuck3.R

class ProductAdapter(private val names: List<String>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.productName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = names[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }


}*/


package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fuck3.databinding.ProductItemBinding
import com.bignerdranch.android.fuck3.ui.models.Product

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var _context: Context
    private lateinit var items: List<Product>

    companion object Factory{

        fun create(context: Context): ProductAdapter{
            val x = ProductAdapter()
            x._context = context
            return x
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvProductName.text = item.name

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvProductName = binding.productName
    }

    fun refreshProducts(items: List<Product>){
        this.items = items
    }
}
