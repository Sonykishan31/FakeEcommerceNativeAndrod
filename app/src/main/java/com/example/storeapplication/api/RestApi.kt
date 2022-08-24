package com.example.storeapplication.api

import com.example.storeapplication.model.CategoryListModel
import com.example.storeapplication.model.ProductListModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET




public interface RestApi {

    @GET("/products")
    fun getProductList(): Call<List<ProductListModel>>

    @GET("/products/categories")
    fun getAllCategories(): Call<CategoryListModel>

}
