package com.example.a3dmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.a3dmarket.databinding.FragmentTopBarBinding

class topBar : Fragment() {
    private val viewModel: CatalogoVM by activityViewModels()
    private lateinit var binding: FragmentTopBarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopBarBinding.inflate(inflater, container, false)
        binding.name.text = viewModel.user.value?.name
        binding.name.setOnClickListener {
            val fragment = perfilPropio()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.imageView4.setOnClickListener {
            val fragment = CatalogoEncargos()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.logout.setOnClickListener {
            val fragment = Login()
            viewModel.logout()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

}