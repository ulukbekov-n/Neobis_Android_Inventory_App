package com.example.neobis_android_inventory_app.fragments

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.Product
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.databinding.FragmentAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.*
import java.util.UUID


class AddFragment : Fragment() {


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

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.addProductImage.setOnClickListener(){
            Log.d("AddFragment", "Try to show image")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
    }
    var selectedImageUri: Uri? = null
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val contentResolver = requireContext().contentResolver
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
//
//            Log.d("AddFragment", "Try to show image")
//            val selectedImageUri = data.data
//            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,selectedImageUri)
//
//            val bitmapDrawable = BitmapDrawable(resources, bitmap)
//            binding.addProductImage.setBackgroundDrawable(bitmapDrawable)
//        }
//    }
private fun pickImageFromGallery() {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, 0)
}
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        val uri = data.data
        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)

        val imagePath = saveImageToInternalStorage(bitmap)

        val imageBitmap = loadImageFromStorage(imagePath)
    }
}
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }
    fun loadImageFromStorage(path: String): Bitmap? {
        return try {
            val f = File(path)
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            b
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun insertDataToDatabase() {

        val modelName = binding.addModelName.text.toString()
        val cost= binding.addCost.text.toString()
        val companyName = binding.addCompanyName.text.toString()
        val quantity = binding.addQuantity.text.toString()
        val productImage = binding.addProductImage.drawToBitmap()
        if (inputCheck(modelName, cost, companyName,quantity)){
            val product = Product(0,modelName, cost,companyName,quantity, productImage)
            mViewModel.addProduct(product)

            Toast.makeText(requireContext(),"Товар добавлен", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
        else{
            Toast.makeText(requireContext(),"Не все поля заполнены",Toast.LENGTH_LONG).show()
        }

    }





    private fun inputCheck(modelName: String,Cost:String,companyName: String,Quantity:String):Boolean{
        return !(TextUtils.isEmpty(modelName) || TextUtils.isEmpty(Cost) || TextUtils.isEmpty(companyName) || TextUtils.isEmpty(Quantity))
    }

}
class Product