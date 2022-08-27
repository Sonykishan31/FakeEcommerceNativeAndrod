package com.example.storeapplication.adapter

import android.util.Log
import com.example.storeapplication.model.ProductListModel
import com.example.storeapplication.listener.StoreItemClicklistener
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.storeapplication.R
import com.example.storeapplication.application.MyApplication
import com.example.storeapplication.entities.MyCart
import com.example.storeapplication.entities.ProductListEntity
import com.example.storeapplication.view.MainActivity
import kotlinx.android.synthetic.main.product_list_layout.view.*

class ProductListAdapter(
    var listData: List<ProductListEntity>,
    var storeItemClicklistener: StoreItemClicklistener
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])

        holder.itemView.setOnClickListener {
            storeItemClicklistener.onProductItemClick(holder.adapterPosition)
        }

        Log.e("System out","Product Qty is : ${MyCart.getItemQty(listData[holder.adapterPosition].getId())}")
    }
    override fun getItemCount(): Int {
        return listData.size
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         fun bind(dataObj:ProductListEntity) = with(itemView){
             productImageSdv.setImageURI(dataObj.image)
             productNameTv.text = dataObj.title
             productDescriptionTv.text = dataObj.description
             productPriceTv.text = "${MyApplication.instance.getString(R.string.Rs)} ${dataObj.price}"
             productRatingTv.text = ""+dataObj.rating?.rate
         }
     }
}