package com.example.storeapplication.application



import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.orm.SugarContext


class MyApplication : Application() {


    companion object {
        lateinit var instance: MyApplication





    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
        SugarContext.init(instance)


    }





}