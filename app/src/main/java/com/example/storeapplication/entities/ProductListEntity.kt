package com.example.storeapplication.entities


import android.media.Rating
import com.example.storeapplication.abstraction.AbstractDataModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductListEntity(

    var category: String? = null,

    var description: String? = null,

    var id: Int? = null,

    var image: String? = null,

    var price: Double? = null,

    var rating: RatingsEntity? = null,

    var title: String? = null,

    var isAddedToCart : Boolean = false

) : AbstractDataModel<ProductListEntity>(), Serializable

class RatingsEntity(

    var count: Int? = null,
    var productId: Int = 0,
    var rate: Double? = null
) : AbstractDataModel<Rating>(), Serializable