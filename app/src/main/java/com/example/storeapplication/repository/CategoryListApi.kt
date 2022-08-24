package com.example.storeapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapplication.api.RestCallback
import com.example.storeapplication.api.RestClient
import com.example.storeapplication.model.CategoryListModel
import com.example.storeapplication.model.ProductListModel


class CategoryListApi(mContext: Context?) : RestCallback<CategoryListModel>(mContext) {

    val data = MutableLiveData<CategoryListModel>()
    fun getCategoryList(): LiveData<CategoryListModel> {


        val call = RestClient.get()!!.getAllCategories()
        call.enqueue(this)
        return data
    }

    override fun Success(response: retrofit2.Response<CategoryListModel>) {
        data.value = response.body()

    }

    override fun failure() {
        data.value = null
    }

}