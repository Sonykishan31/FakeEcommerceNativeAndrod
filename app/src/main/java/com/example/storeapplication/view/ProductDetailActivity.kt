package com.example.storeapplication.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.storeapplication.R
import com.example.storeapplication.api.RestClient
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.MyCart
import com.example.storeapplication.entities.ProductListEntity
import com.example.storeapplication.entities.RatingsEntity
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.product_list_layout.view.*
import kotlinx.android.synthetic.main.toolbar.*


class ProductDetailActivity : AppCompatActivity() {

    var productDetail : ProductListEntity ?= null
    val activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleText.text = resources.getString(R.string.product_detail)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        if (intent.hasExtra("productId")){
            val prodId = intent.extras
                ?.get("productId")
            productDetail = SugarRecord.find(ProductListEntity::class.java,"id = ?","${prodId}")[0]
            setDataToViews()
        }

    }

    private fun setDataToViews() {


        val ratingObj= SugarRecord.find(RatingsEntity::class.java, "product_id = ?", "${productDetail?.getId()}").get(0)
        if (ratingObj!=null){
            productDetail?.rating = ratingObj
        }
        productDetailImageSdv.setImageURI(productDetail?.image)
        productDetailNameTv.text = productDetail?.title
        productDetailDescriptionTv.text = productDetail?.description
        productDetailPriceTv.text = "${MyApplication.instance.getString(com.example.storeapplication.R.string.Rs)} ${productDetail?.price}"
        productDetailRatingTv.text = ""+productDetail?.rating?.rate
        if (MyCart.getItemQty(productDetail?.getId()!!) > 0) {
            cartAddRemoveBtnMainLL.visibility = View.VISIBLE
            qtyText.text = ""+ MyCart.getItemQty(productDetail?.getId()!!)
            addToCartBtn.visibility = View.GONE
        }
        else {
            cartAddRemoveBtnMainLL.visibility = View.GONE
            addToCartBtn.visibility = View.VISIBLE
        }
    }
}