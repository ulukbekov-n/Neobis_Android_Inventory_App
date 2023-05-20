package com.example.neobis_android_inventory_app.fragments.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.AlertDialogBuilder
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var productList= emptyList<Product>()
    private lateinit var builder:AlertDialog.Builder
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val modelName: TextView = itemView.findViewById(R.id.nameModel)
        val modelCost: TextView = itemView.findViewById(R.id.modelCost)
        val companyName: TextView = itemView.findViewById(R.id.nameCompany)
        val quantity: TextView = itemView.findViewById(R.id.modelQuantity)
        val imageView: View = itemView.findViewById(R.id.productImage)

        val dots:View = itemView.findViewById(R.id.dots)

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
        builder = AlertDialog.Builder(context)
        holder.dots.setOnClickListener{
            showBottomSheet(currentItem)
        }
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }


        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(currentItem.image)
            .into(holder.imageView as ImageView)




    }
    fun setData(product:List<Product>){
        this.productList =product
        notifyDataSetChanged()
    }
    private fun showBottomSheet(currentItem: Product) {
        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)
        val modelName = currentItem.modelName

        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(bottomSheetView)

        val toArchiveTextView = bottomSheetView.findViewById<TextView>(R.id.toArchive)
        toArchiveTextView.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setMessage("Архивировать $modelName из каталога?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialogInterface, _ ->
                    Toast.makeText(context, "Archiving $modelName from the catalog", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Нет") { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .create()
            alertDialog.show()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }





}