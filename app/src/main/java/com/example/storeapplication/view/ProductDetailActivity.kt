package com.example.storeapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storeapplication.R
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.model.ProductListModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.product_list_layout.*
import kotlinx.android.synthetic.main.product_list_layout.view.*

class ProductDetailActivity : AppCompatActivity() {

    var productDetail : ProductListModel ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="Product Detail"
        setContentView(R.layout.activity_product_detail)

        if (intent.hasExtra("productDetail")){
            productDetail = intent.extras?.get("productDetail") as ProductListModel?
            setDataToViews()
        }

    }

    private fun setDataToViews() {
        productDetailImageSdv.setImageURI(productDetail?.image)
        productDetailNameTv.text = productDetail?.title
        productDetailDescriptionTv.text = productDetail?.description
        productDetailPriceTv.text = "${MyApplication.instance.getString(R.string.Rs)} ${productDetail?.price}"
        productDetailRatingTv.text = ""+productDetail?.rating?.rate
    }
}