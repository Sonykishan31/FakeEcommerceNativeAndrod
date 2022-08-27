package com.example.storeapplication.entities

import com.example.storeapplication.abstraction.AbstractDataModel

class CategoriesEntity (
    var categoryName : String = "",
    var isSelected : Boolean= false
) : AbstractDataModel<CategoriesEntity>()