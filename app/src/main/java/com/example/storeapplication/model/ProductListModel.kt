package com.example.storeapplication.model


import android.media.Rating
import com.example.storeapplication.abstraction.AbstractDataModel
import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord
import java.io.Serializable

class ProductListModel(
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("price")
    var price: Double? = null,
    @SerializedName("rating")
    var rating: Ratings? = null,
    @SerializedName("title")
    var title: String? = null
) : Serializable

class Ratings(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("rate")
    var rate: Double? = null
) :  Serializable