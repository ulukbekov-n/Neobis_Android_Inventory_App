package com.example.neobis_android_inventory_app.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.Product

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var productList= emptyList<Product>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val modelName: TextView = itemView.findViewById(R.id.nameModel)
        val Cost: TextView = itemView.findViewById(R.id.modelCost)
        val companyName: TextView = itemView.findViewById(R.id.nameCompany)
        val quantity: TextView = itemView.findViewById(R.id.modelQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.customer_cards,parent,false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.modelName.text=currentItem.modelName
        holder.Cost.text=currentItem.Cost
        holder.companyName.text=currentItem.companyName
        holder.quantity.text=currentItem.Quantity
    }
    fun setData(product:List<Product>){
        this.productList =product
        notifyDataSetChanged()
    }
}