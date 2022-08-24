package com.example.storeapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapplication.api.RestCallback
import com.example.storeapplication.api.RestClient
import com.example.storeapplication.model.ProductListModel


class ProductListApi(mContext: Context?) : RestCallback<List<ProductListModel>>(mContext) {

    val data = MutableLiveData<List<ProductListModel>>()
    fun getProductList(): LiveData<List<ProductListModel>> {


        val call = RestClient.get()!!.getProductList()
        call.enqueue(this)
        return data
    }

    override fun Success(response: retrofit2.Response<List<ProductListModel>>) {
        data.value = response.body()

    }

    override fun failure() {
        data.value = null
    }

}