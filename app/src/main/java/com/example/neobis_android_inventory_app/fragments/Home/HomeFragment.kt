package com.example.neobis_android_inventory_app.fragments.Home

import ArchiveViewModel
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
import com.example.neobis_android_inventory_app.fragments.ArchiveAdapter
import com.example.neobis_android_inventory_app.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mViewModel: ProductViewModel
    private lateinit var myAdapter: HomeAdapter
    private lateinit var archiveAdapter: ArchiveAdapter

    private lateinit var archiveViewModel: ArchiveViewModel
    private lateinit var dialog:BottomSheetDialog
    private lateinit var sharedViewModel: HomeAdapter.SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(HomeAdapter.SharedViewModel::class.java)
    }
    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        archiveViewModel = ArchiveViewModel()
        archiveAdapter = ArchiveAdapter(requireContext())
        dialog = BottomSheetDialog(requireContext())
        myAdapter = HomeAdapter(requireContext(), this, archiveAdapter, archiveViewModel, HomeAdapter.SharedViewModel())
        val recyclerViewView = binding.recyclerview1
        val searchView = binding.searchView
        recyclerViewView.adapter = myAdapter
        recyclerViewView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.floatButton.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_addFragment) }

        mViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java) // Fix here

        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { product ->
            myAdapter.setData(product)
        })


        sharedViewModel.selectedItem.observe(viewLifecycleOwner) { selectedItem ->

            val action = HomeFragmentDirections.actionHomeFragmentToArchiveFragment()
            findNavController().navigate(action)
        }
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
    fun moveItemToArchive(position: Int, item: Product) {
         archiveViewModel.addItem(item   )

        archiveAdapter.setData(archiveViewModel.getItems().value ?: emptyList())
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
