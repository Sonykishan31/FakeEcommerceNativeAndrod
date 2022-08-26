package com.example.storeapplication.entities

import com.example.storeapplication.abstraction.AbstractDataModel

class MyCart (

    var quantity: Int? = null,
    //this will be the product id
    var id : Int

) : AbstractDataModel<MyCart>()