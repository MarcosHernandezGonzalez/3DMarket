package com.example.a3dmarket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a3dmarket.databinding.FragmentCatalogoEncargosBinding
import com.example.a3dmarket.CatalogoVM
import kotlin.math.log

class CatalogoEncargos : Fragment() {
    private lateinit var binding: FragmentCatalogoEncargosBinding
    private val viewModel: CatalogoVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogoEncargosBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = CatalogoItemAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            adapter.updateData(orders)
            Log.d("CatalogoEncargos", "Pedidos cargados: ${orders.size}")
        }

        viewModel.fetchOrders()

        binding.crearEncargo.setOnClickListener {
            val fragment = CreateOrder()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

}
