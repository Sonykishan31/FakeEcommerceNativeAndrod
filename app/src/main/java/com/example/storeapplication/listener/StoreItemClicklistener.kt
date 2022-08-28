package com.example.storeapplication.listener



interface StoreItemClicklistener {

    fun onProductItemClick(index: Int)
    fun onCategoryItemClick(index: Int)
    fun addToCartActin(index: Int)
    fun removeFromCartAction(index: Int)
}