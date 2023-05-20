package com.example.neobis_android_inventory_app.fragments.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mViewModel: ProductViewModel
    private lateinit var myAdapter: HomeAdapter
    private lateinit var dialog:BottomSheetDialog
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        myAdapter = HomeAdapter(requireContext())
        val recyclerViewView = binding.recyclerview1
        val searchView = binding.searchView
        recyclerViewView.adapter = myAdapter


        recyclerViewView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.floatButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_addFragment) }
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { product ->
            myAdapter.setData(product)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mViewModel.searchProducts(newText.orEmpty()).observe(viewLifecycleOwner, Observer { filteredProducts ->
                    myAdapter.setData(filteredProducts)
                })
                return true
            }

        })



        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<FloatingActionButton>(R.id.floatButton).hide()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<FloatingActionButton>(R.id.floatButton).show()
    }

    private fun showBottomSheet(){
        val dialogView = layoutInflater.inflate(R.layout.bottomsheet,null)
        dialog.setContentView(dialogView)
        dialog.show()
    }


}