package com.example.a3dmarket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.a3dmarket.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: CatalogoVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.register.setOnClickListener {
            val fragment = Register()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.login(email, pass)
            }
        }
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                val fragment = CatalogoEncargos()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}
