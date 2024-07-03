package com.bignerdranch.android.fuck3.ui.dashboard

import adapters.ProductAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.fuck3.databinding.FragmentProductsBinding
import com.bignerdranch.android.fuck3.ui.models.Product

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var context: Context
    private lateinit var adapter: ProductAdapter
    private lateinit var products: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        products = listOf(
            Product(1, "hi"),
            Product(2, "hello world"),
            Product(3, "goodbye space"),
            Product(4, "goodbye space"),
            Product(5, "goodbye space"),
            Product(6, "goodbye space"),
            Product(7, "goodbye space"),
            Product(8, "goodbye space"),
            Product(9, "goodbye space"),

        )

        binding = FragmentProductsBinding.inflate(inflater, container, false)

        context = this.requireContext()

        adapter = ProductAdapter.create(context)

        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        binding.rvProducts.adapter = adapter

        adapter.refreshProducts(products)

        var dashboardViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        return binding.root
    }
}