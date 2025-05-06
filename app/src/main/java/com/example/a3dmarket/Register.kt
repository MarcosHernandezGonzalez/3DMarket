package com.example.a3dmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.a3dmarket.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class Register : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: CatalogoVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.login.setOnClickListener {
            val fragment = Login()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.register.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.desc.text.toString()
            val pass = binding.pass.text.toString()
            val printerInfo = binding.printerInfo.text.toString()
            val dir = binding.dir.text.toString()
            val isMaker = binding.isMaker.isChecked
            val showEmail = binding.showEmail.isChecked
            val showLocation = binding.showLocation.isChecked

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()) {
                if ((isMaker && printerInfo.isNotEmpty()) || !isMaker) {
                    lifecycleScope.launch {
                        val usernameExists = viewModel.checkUsername(name)
                        val emailExists = viewModel.checkEmail(email)

                        if (usernameExists) {
                            Toast.makeText(
                                requireContext(),
                                "El nombre de usuario ya está en uso",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (emailExists) {
                            Toast.makeText(
                                requireContext(),
                                "El correo electrónico ya está en uso",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.registerUser(
                                name,
                                email,
                                pass,
                                printerInfo,
                                dir,
                                isMaker,
                                showEmail,
                                showLocation
                            )

                            val fragment = Login()
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView2, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Por favor, rellena la información de la impresora", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }
}