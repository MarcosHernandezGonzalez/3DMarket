package com.example.a3dmarket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a3dmarket.databinding.FragmentPerfilBinding

class Perfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private val viewModel: CatalogoVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        if (viewModel.userId.value == viewModel.userInfo.value?.id) {
            val fragment = perfilPropio()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        if (viewModel.userInfo.value?.isMaker == true) {
            binding.isMaker.text = "Maker"
        } else {
            binding.isMaker.text = "No Maker"
        }
        binding.name.text = viewModel.userInfo.value?.name
        binding.email.text = viewModel.userInfo.value?.email
        binding.location.text = viewModel.userInfo.value?.dir
        binding.printerInfo.text = viewModel.userInfo.value?.printerInfo
        val recyclerView = binding.userOrders
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = CatalogoItemAdapter(emptyList())
        recyclerView.adapter = adapter
        viewModel.userOrders.observe(viewLifecycleOwner) { orders ->
            adapter.updateData(orders)
            Log.d("CatalogoEncargos", "Pedidos cargados: ${orders.size}")
        }
        viewModel.getUserOrders(viewModel.userInfo.value?.id.toString())
        return binding.root
    }

}