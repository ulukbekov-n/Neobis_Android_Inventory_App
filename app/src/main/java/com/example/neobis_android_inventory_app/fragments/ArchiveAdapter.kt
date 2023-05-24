package com.example.neobis_android_inventory_app.fragments

import ArchiveViewModel
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.fragments.Home.HomeAdapter
import com.example.neobis_android_inventory_app.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog

class ArchiveAdapter(private val context: Context) : RecyclerView.Adapter<ArchiveAdapter.MyViewHolder>() {

    private var itemList = mutableListOf<Product>()
    private lateinit var builder: AlertDialog.Builder
    private lateinit var homeAdapter:HomeAdapter

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelName: TextView = itemView.findViewById(R.id.nameModel)
        val modelCost: TextView = itemView.findViewById(R.id.modelCost)
        val companyName: TextView = itemView.findViewById(R.id.nameCompany)
        val quantity: TextView = itemView.findViewById(R.id.modelQuantity)
        val imageView: View = itemView.findViewById(R.id.productImage)
        val dots: View = itemView.findViewById(R.id.dots)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.customer_cards, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.modelName.text = currentItem.modelName
        holder.modelCost.text = currentItem.Cost
        holder.companyName.text = currentItem.companyName
        holder.quantity.text = currentItem.Quantity
        builder = AlertDialog.Builder(context)
        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(currentItem.image)
            .into(holder.imageView as ImageView)
        holder.dots.setOnClickListener {
            showBottomSheet(context ,position, currentItem)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(items: List<Product>) {

        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
        Log.d("ArchiveAdapter", "Items set: $items")
    }


    private fun showBottomSheet(context: Context, position: Int, currentItem: Product) {
        val bottomSheetView =
            LayoutInflater.from(context).inflate(R.layout.bottomsheetarch, null)
        val modelName = currentItem.modelName

        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(bottomSheetView)

        val toArchiveTextView = bottomSheetView.findViewById<TextView>(R.id.toDeleteArchive)
        toArchiveTextView.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setMessage("Удалить $modelName из архива?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialogInterface, _ ->
                    removeItem(position)
                }
                .setNegativeButton("Нет") { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .create()
            alertDialog.show()
            bottomSheetDialog.dismiss()
        }
        val toUnArchive = bottomSheetView.findViewById<TextView>(R.id.toArchive)
        toUnArchive.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setMessage("Удалить $modelName из архива?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialogInterface, _ ->
                    unarchiveItem(position, currentItem)
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

    private fun unarchiveItem(position: Int, item: Product) {
        val newItem = item.copy(archived = false) // Create a copy of the item with archived set to false
        moveItemToHome(position, newItem)
        removeItem(position)
    }

    private fun moveItemToHome(position: Int, item: Product) {
        val archiveViewModel = ArchiveViewModel()
        archiveViewModel.removeItem(position)
        homeAdapter.addItem( item )
    }
    fun removeItem(position: Int) {
        val newList = itemList.toMutableList()
        newList.removeAt(position)
        itemList = newList
        notifyItemRemoved(position)
    }


}

