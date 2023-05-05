package com.example.neobis_android_inventory_app.fragments.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mViewModel: ProductViewModel
    private lateinit var myAdapter: HomeAdapter
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        myAdapter = HomeAdapter()
        val recyclerViewView = binding.recyclerview1
        recyclerViewView.adapter = myAdapter

        recyclerViewView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.floatButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_addFragment) }
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { product ->
            myAdapter.setData(product)
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


}