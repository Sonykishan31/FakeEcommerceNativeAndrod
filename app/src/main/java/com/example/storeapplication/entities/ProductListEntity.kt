package com.example.storeapplication.entities


import android.media.Rating
import com.example.storeapplication.abstraction.AbstractDataModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductListEntity(

    @Expose
    var category: String? = null,

    @Expose
    var description: String? = null,

    @Expose
    @SerializedName("id")
    var id: Int? = null,

    @Expose
    var image: String? = null,

    @Expose
    var price: Double? = null,

    @Expose
    var rating: RatingsEntity? = null,

    @Expose
    var title: String? = null

) : AbstractDataModel<ProductListEntity>()

class RatingsEntity(

    var count: Int? = null,
    var productId: Int = 0,
    var rate: Double? = null
) : AbstractDataModel<Rating>()