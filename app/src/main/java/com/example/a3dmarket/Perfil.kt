package com.example.a3dmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.a3dmarket.databinding.FragmentPerfilBinding

class Perfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private val viewModel: CatalogoVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        /*
        if (viewModel.userInfo.value?.isMaker == true) {
            binding.isMaker.text = "Maker"
        } else {
            binding.isMaker.text = "No Maker"

        }
        */
        binding.isMaker.text = viewModel.userInfo.value?.isMaker.toString()
        binding.name.text = viewModel.userInfo.value?.name
        binding.email.text = viewModel.userInfo.value?.email
        binding.location.text = viewModel.userInfo.value?.dir
        binding.printerInfo.text = viewModel.userInfo.value?.printerInfo
        return binding.root
    }

}