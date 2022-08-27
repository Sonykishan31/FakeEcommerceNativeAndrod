package com.example.storeapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.storeapplication.R
import com.example.storeapplication.api.RestClient
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.ProductListEntity
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_product_detail.*
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


        if (intent.hasExtra("productDetail")){
            productDetail = intent.extras?.get("productDetail") as ProductListEntity?
            setDataToViews()
        }

    }

    private fun setDataToViews() {
        productDetailImageSdv.setImageURI(productDetail?.image)
        productDetailNameTv.text = productDetail?.title
        productDetailDescriptionTv.text = productDetail?.description
        productDetailPriceTv.text = "${MyApplication.instance.getString(com.example.storeapplication.R.string.Rs)} ${productDetail?.price}"
        productDetailRatingTv.text = ""+productDetail?.rating?.rate

        productDetailsAddToCartLL.setOnClickListener {
            if (productDetail?.isAddedToCart!!){

            }
            else {

            }
        }

        changeBtnState(productDetail?.isAddedToCart)
    }

    private fun changeBtnState(addedToCart: Boolean?) {

        if (addedToCart!!){
            productDetailsAddToCartLL.setBackgroundColor(ContextCompat.getColor(activity,R.color.green))
            productDetailAddToCartTv.text = getString(R.string.remove_from_cart)
        }
        else{
            productDetailsAddToCartLL.setBackgroundColor(ContextCompat.getColor(activity,R.color.purple_700))
            productDetailAddToCartTv.text = getString(R.string.add_to_cart)
        }
    }
}