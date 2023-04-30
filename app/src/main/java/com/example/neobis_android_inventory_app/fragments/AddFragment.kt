package com.example.neobis_android_inventory_app.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.Product
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.databinding.FragmentAddBinding


class addFragment : Fragment() {

     private    lateinit var mViewModel:ProductViewModel
    private lateinit var binding:FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener{
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val modelName = binding.addModelName.text.toString()
        val cost= binding.addCost.text.toString()
        val companyName = binding.addCompanyName.text.toString()
        val quantity = binding.addQuantity.text.toString()

        if (inputCheck(modelName, cost, companyName,quantity)){
            val product = Product(0,modelName, cost,companyName,quantity )
            mViewModel.addProduct(product)
            Toast.makeText(requireContext(),"Товар добавлен", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
        else{
            Toast.makeText(requireContext(),"Не все поля заполнены",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(modelName: String,Cost:String,companyName: String,Quantity:String):Boolean{
        return !(TextUtils.isEmpty(modelName)&& TextUtils.isEmpty(Cost)&& TextUtils.isEmpty(companyName)&& TextUtils.isEmpty(Quantity))
    }



}