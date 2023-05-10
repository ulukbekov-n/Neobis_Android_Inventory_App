package com.example.neobis_android_inventory_app.fragments.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.model.Product
import com.example.neobis_android_inventory_app.databinding.CustomerCardsBinding
import com.example.neobis_android_inventory_app.databinding.FragmentHomeBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var productList= emptyList<Product>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val modelName: TextView = itemView.findViewById(R.id.nameModel)
        val modelCost: TextView = itemView.findViewById(R.id.modelCost)
        val companyName: TextView = itemView.findViewById(R.id.nameCompany)
        val quantity: TextView = itemView.findViewById(R.id.modelQuantity)
        val imageView: View = itemView.findViewById(R.id.productImage)

        val cardItem:View = itemView.findViewById(R.id.cardItem)
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
        holder.modelCost.text=currentItem.Cost
        holder.companyName.text=currentItem.companyName
        holder.quantity.text=currentItem.Quantity


        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(currentItem.image)
            .into(holder.imageView as ImageView)




    }
    fun setData(product:List<Product>){
        this.productList =product
        notifyDataSetChanged()
    }

}