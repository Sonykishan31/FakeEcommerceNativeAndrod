package com.example.storeapplication.entities


import android.media.Rating
import com.example.storeapplication.abstraction.AbstractDataModel

class ProductListEntity(

    var category: String? = null,

    var description: String? = null,

    var id: Int? = null,

    var image: String? = null,

    var price: Double? = null,

    var rating: RatingsEntity? = null,

    var title: String? = null

) : AbstractDataModel<ProductListEntity>()

class RatingsEntity(

    var count: Int? = null,
    var productId: Int = 0,
    var rate: Double? = null
) : AbstractDataModel<Rating>()