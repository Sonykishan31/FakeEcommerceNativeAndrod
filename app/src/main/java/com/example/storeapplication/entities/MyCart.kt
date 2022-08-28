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

        fun addItemToCart(productId:Int){

            if (isExist(productId)){
                val obj = SugarRecord.find(MyCart::class.java,"product_id = ?","${productId}")[0]
                obj.quantity +=1
                obj.save()
            }
            else {
                val obj  = MyCart()
                obj.productId = productId
                obj.quantity = obj.quantity+1
                obj.save()
            }

        }

        fun removeItemFromCart(productId:Int){

            if (isExist(productId)){
                val obj = SugarRecord.find(MyCart::class.java,"product_id = ?","${productId}")[0]
                if (obj.quantity > 1){
                    obj.quantity =obj.quantity-1
                    obj.save()
                }
                else {
                    SugarRecord.delete(obj)
                }
            }

        }

        fun calculateTotal () : Double {

            if (SugarRecord.listAll(MyCart::class.java).isEmpty()){
                return 0.0
            }

            val productList = SugarRecord.listAll(ProductListEntity::class.java)

            var totalAmount = 0.0
            if (productList.isNotEmpty()){

                for (i in 0 until productList.size){
                    if (isExist(productList[i].getId().toInt())){
                        val qty = getItemQty(productList[i].getId())

                        totalAmount += (productList[i].price!! * qty)

                    }
                }

            }
            return totalAmount
        }

    }

}

