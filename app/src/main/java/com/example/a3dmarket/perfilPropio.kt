package com.example.a3dmarket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a3dmarket.databinding.FragmentPerfilPropioBinding

class perfilPropio : Fragment() {
    private val viewModel: CatalogoVM by activityViewModels()
    private lateinit var binding: FragmentPerfilPropioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilPropioBinding.inflate(inflater, container, false)
        if (viewModel.user.value?.isMaker == true) {
            binding.isMaker.text = "Maker"
        } else {
            binding.isMaker.text = "No Maker"
        }
        binding.name.text = viewModel.user.value?.name
        binding.email.text = viewModel.user.value?.email
        binding.location.text = viewModel.user.value?.dir
        binding.printerInfo.text = viewModel.user.value?.printerInfo
        val recyclerView = binding.userOrders
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = CatalogoItemAdapter(emptyList())
        recyclerView.adapter = adapter
        viewModel.userOrders.observe(viewLifecycleOwner) { orders ->
            adapter.updateData(orders)
            Log.d("CatalogoEncargos", "Pedidos cargados: ${orders.size}")
        }
        viewModel.getUserOrders(viewModel.user.value?.id.toString())
        return binding.root
    }

}