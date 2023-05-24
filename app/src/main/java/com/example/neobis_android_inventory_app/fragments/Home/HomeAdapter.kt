package com.example.neobis_android_inventory_app.fragments.Home

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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.fragments.ArchiveAdapter
import com.example.neobis_android_inventory_app.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeAdapter(private val context: Context, private val homeFragment: HomeFragment,
                  private val archiveAdapter: ArchiveAdapter,
                  private val archiveViewModel: ArchiveViewModel,
                  private val sharedViewModel: SharedViewModel) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

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
    class SharedViewModel : ViewModel() {
        private val _selectedItem = MutableLiveData<Product>()
        val selectedItem: LiveData<Product> get() = _selectedItem

        val archivedItems: MutableLiveData<List<Product>> = MutableLiveData()


        fun selectItem(item: Product) {
            _selectedItem.value = item
        }
    }
    fun moveItemToArchive(position: Int, item: Product) {
        archiveViewModel.addItem(item)
        archiveAdapter.setData(archiveViewModel.getItems().value ?: emptyList())
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
            showBottomSheet(position,currentItem)
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
    private fun showBottomSheet(position: Int, currentItem: Product) {
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
                    moveItemToArchive(position, currentItem)
                    removeItem(position)


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

    val archivedItems: MutableLiveData<List<Product>> = MutableLiveData()
    fun addItem(item: Product) {

        val currentItems = archivedItems.value ?: emptyList()
        val updatedItems = currentItems + item
        archivedItems.value = updatedItems


        Log.d("ArchiveViewModel", "Item added: $item")
        Log.d("ArchiveViewModel", "Updated items: $updatedItems")
    }
    private fun removeItem(position: Int) {
        val newList = productList.toMutableList()
        newList.removeAt(position)
        productList = newList
        notifyItemRemoved(position)
    }


}