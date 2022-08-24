package com.example.storeapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.model.CategoryListModel
import com.example.storeapplication.model.ProductListModel
import com.example.storeapplication.repository.CategoryListApi
import com.example.storeapplication.repository.ProductListApi

class CategoryListViewModel():ViewModel() {

    fun getCategoryListResponseLiveData(): LiveData<CategoryListModel> {
        return CategoryListApi(MyApplication.instance).getCategoryList()
    }
}