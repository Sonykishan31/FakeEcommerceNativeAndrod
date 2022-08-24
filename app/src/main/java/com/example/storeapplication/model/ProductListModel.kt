package com.example.storeapplication.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductListModel(
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
    var rating: Rating? = null,
    @SerializedName("title")
    var title: String? = null
) : Serializable

data class Rating(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("rate")
    var rate: Double? = null
) : Serializable