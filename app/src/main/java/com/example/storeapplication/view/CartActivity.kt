package com.example.storeapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeapplication.R
import com.example.storeapplication.adapter.ProductListAdapter
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.MyCart
import com.example.storeapplication.entities.ProductListEntity
import com.example.storeapplication.entities.RatingsEntity
import com.example.storeapplication.listener.StoreItemClicklistener
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_cart_screen.*



import kotlinx.android.synthetic.main.toolbar.*

class CartActivity : AppCompatActivity(), StoreItemClicklistener {

    val activity = this
    var productList: ArrayList<ProductListEntity> = ArrayList()
    var productListAdapter: ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_screen)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleText.text = resources.getString(R.string.my_cart)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        cartTotalMainLL.setOnClickListener {
            finish()
            startActivity(Intent(activity,OrderSuccessfullActivity::class.java))
        }

        initialization()
        fetchDataFromCart()
    }

    private fun fetchDataFromCart() {
        val cartItems = SugarRecord.listAll(MyCart::class.java)
        productList.clear()
        for (i in 0 until cartItems.size){
            val listData = SugarRecord.find(ProductListEntity::class.java,"id = ?","${cartItems[i].productId}")
            for (j in 0 until listData.size){
                val ratingObj= SugarRecord.find(RatingsEntity::class.java, "product_id = ?", "${listData[j].getId()}").get(0)
                if (ratingObj!=null){
                    listData[j].rating = ratingObj
                }
                productList.add(listData[j])
            }
        }

        if (productList.isNotEmpty()){
            cartRootLL.visibility = View.VISIBLE
            noDataFoundTv.visibility = View.GONE
        }
        else
        {
            cartRootLL.visibility = View.GONE
            noDataFoundTv.visibility = View.VISIBLE
        }

        handleTotalView()
        productListAdapter?.notifyDataSetChanged()
    }

    private fun initialization() {
        cartScreenRv.layoutManager = LinearLayoutManager(activity)
        // ProduceList adapter
        productListAdapter = ProductListAdapter(productList, this)
        cartScreenRv.adapter = productListAdapter
        cartScreenRv.setHasFixedSize(true)
    }

    override fun onProductItemClick(index: Int) {
//        val i = Intent(activity, ProductDetailActivity::class.java)
//        i.putExtra("productId", productList[index].getId())
//        startActivity(i)
    }

    override fun onCategoryItemClick(index: Int) {
        // This implementation not needed because adapter is reused here!
    }

    override fun addToCartActin(index: Int) {
        MyCart.addItemToCart(productList[index].getId().toInt())
        fetchDataFromCart()
    }

    override fun removeFromCartAction(index: Int) {
        MyCart.removeItemFromCart(productList[index].getId().toInt())
        fetchDataFromCart()
    }

    fun handleTotalView(){

        val totalAmount = MyCart.calculateTotal()
        cartTotalMainTv.text = "${MyApplication.instance.getString(R.string.Rs)} $totalAmount"
        if (totalAmount > 0){
            cartTotalMainLL.visibility = View.VISIBLE
        }
        else {
            cartTotalMainLL.visibility = View.GONE
        }
    }

}