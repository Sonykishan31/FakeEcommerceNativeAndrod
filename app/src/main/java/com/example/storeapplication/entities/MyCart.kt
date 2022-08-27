package com.example.storeapplication.entities

import com.example.storeapplication.abstraction.AbstractDataModel
import com.orm.SugarRecord

class MyCart : AbstractDataModel<MyCart>() {
    var quantity: Int =0;
    //this will be the product id
    var productId : Int = 0;


    companion object {
        fun getItemQty(productId:Long) : Int {

            if (!isExist(productId.toInt())){
                return  0
            }

            return SugarRecord.find(MyCart::class.java,"product_id = ?","${productId}")[0].quantity!!
        }

        fun isExist(productId: Int) : Boolean {

            return SugarRecord.find(MyCart::class.java,"product_id = ?","${productId}").isNotEmpty()
        }

        fun addIteTtoCart(productId:Int){

            if (isExist(productId)){
                val obj = SugarRecord.find(MyCart::class.java,"product_id = ?","${productId}")[0]
                obj.quantity +=1
                obj.save()
            }
            else {
                val obj  = MyCart()
                obj.productId = productId
                obj.quantity = obj.quantity+1
            }

        }

        fun removeIteTtoCart(productId:Int){

            if (isExist(productId)){
                val obj = SugarRecord.find(MyCart::class.java,"product_id","${productId}")[0]
                if (obj.quantity > 0){
                    obj.quantity =obj.quantity-1
                    obj.save()
                }
                else {
                    SugarRecord.delete(obj)
                }
            }

        }

    }

}