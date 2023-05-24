package com.example.neobis_android_inventory_app.fragments

import ArchiveViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neobis_android_inventory_app.R
import com.example.neobis_android_inventory_app.data.ProductViewModel
import com.example.neobis_android_inventory_app.model.Product


class ArchiveFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var archiveViewModel: ArchiveViewModel
    private lateinit var archiveAdapter: ArchiveAdapter
    private lateinit var mViewModel:ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var productList= emptyList<Product>()
        val view = inflater.inflate(R.layout.fragment_archive, container, false)
        recyclerView = view.findViewById(R.id.recyclerviewArch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        archiveViewModel = ViewModelProvider(this).get(ArchiveViewModel::class.java)
        archiveAdapter = ArchiveAdapter(requireContext())

        mViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java) // Fix here

        recyclerView.adapter = archiveAdapter

        archiveViewModel.getItems().observe(viewLifecycleOwner) { items ->
            archiveAdapter.setData(items)

        }

        return view
    }

}
