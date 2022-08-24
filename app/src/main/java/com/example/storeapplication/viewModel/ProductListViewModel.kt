package com.example.storeapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.model.ProductListModel
import com.example.storeapplication.repository.ProductListApi

class ProductListViewModel():ViewModel() {

    fun getProductListResponseLiveData(): LiveData<List<ProductListModel>> {
        return ProductListApi(MyApplication.instance).getProductList()
    }
}