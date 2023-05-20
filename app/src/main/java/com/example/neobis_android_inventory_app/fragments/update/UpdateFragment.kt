package com.example.neobis_android_inventory_app.fragments.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.databinding.FragmentUpdateBinding
import com.example.neobis_android_inventory_app.model.Product
import com.google.firebase.components.Qualified


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var  binding: FragmentUpdateBinding
    private lateinit var mViewModel:ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        binding.updateModelName.setText(args.currentProduct.modelName)
        binding.updateCost.setText(args.currentProduct.Cost)
        binding.updateCompanyName.setText(args.currentProduct.companyName)
        binding.updateQuantity.setText(args.currentProduct.Quantity)


        binding.updateButton.setOnClickListener{
            updateItem()
        }
        binding.cancelButton.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.updateProductImage.setOnClickListener() {
            Log.d("AddFragment", "Try to show image")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        return binding.root
    }
    var selectedImageUri: Uri? = null

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Glide.with(this).load(selectedImageUri).into(binding.updateProductImage)
        }
    }
    private fun updateItem(){
        val modelName= binding.updateModelName.text.toString()
        val Cost = binding.updateCost.text.toString()
        val companyName = binding.updateCompanyName.text.toString()
        val Quantity = binding.updateQuantity.text.toString()
        val Image = selectedImageUri.toString()

        if (inputCheck(modelName,Cost,companyName, Quantity)){
            val updatedProduct=Product(args.currentProduct.id, modelName,Cost,companyName,Quantity, Image )

            mViewModel.updateProduct(updatedProduct)
            Toast.makeText(requireContext(),"Товар Сохранён",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)

        }
        else{
            Toast.makeText(requireContext(),"Пожалуйста заполните все данные",Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(
        modelName: String,
        Cost: String,
        companyName: String,
        Quantity: String
    ): Boolean {
        return !(TextUtils.isEmpty(modelName) || TextUtils.isEmpty(Cost) || TextUtils.isEmpty(
            companyName
        ) || TextUtils.isEmpty(Quantity))
    }
}