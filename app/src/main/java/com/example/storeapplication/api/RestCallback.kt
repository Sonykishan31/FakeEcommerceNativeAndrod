package com.example.storeapplication.api

import android.content.Context

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RestCallback<T>(internal var mContext: Context?) : Callback<T> {


    //success response
    abstract fun Success(response: Response<T>)

    //error
    abstract fun failure()

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.body() != null ) {


            Success(response)


        } else {

            failure()

        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        failure()


    }
}
