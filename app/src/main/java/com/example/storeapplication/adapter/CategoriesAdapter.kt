package com.example.storeapplication.adapter

import com.example.storeapplication.listener.StoreItemClicklistener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.storeapplication.R
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.CategoriesEntity
import com.example.storeapplication.model.CategoryModel
import com.example.storeapplication.view.MainActivity
import kotlinx.android.synthetic.main.category_layout.view.*

class CategoriesAdapter(
    activity: MainActivity,
    var data: List<CategoriesEntity>,
    var itemClicklistener: StoreItemClicklistener
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            itemClicklistener.onCategoryItemClick(holder.adapterPosition)
        }

    }
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(data:CategoriesEntity)= with(itemView){
            categoryNameTv.text = data.categoryName

            when {
                data.isSelected -> {
                    categoryMainLL.background = MyApplication.instance.getDrawable(R.drawable.rounded_corner_view_selected)
                }
                else -> {
                    categoryMainLL.background = MyApplication.instance.getDrawable(R.drawable.rounded_corner_view_deselected)
                }
            }
        }
    }
}