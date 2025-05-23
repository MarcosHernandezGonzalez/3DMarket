package com.example.a3dmarket

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.a3dmarket.databinding.FragmentCreateOrderBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateOrder : Fragment() {
    private lateinit var binding: FragmentCreateOrderBinding
    private val viewModel: CatalogoVM by activityViewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOrderBinding.inflate(inflater, container, false)

        binding.create.setOnClickListener {
            if (binding.title.text.toString().isNotEmpty() && binding.desc.text.toString().isNotEmpty() && binding.archivo.text.contains("drive.google.com")) {
                Log.d("CreateOrder", "Botón pulsado")
                val fechaHora = LocalDateTime.now()
                val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd 'a las' HH:mm")
                val fechaFormateada = fechaHora.format(formato)
                viewModel.createOrder(
                    Order(
                        "",
                        binding.title.text.toString(),
                        binding.desc.text.toString(),
                        "",
                        binding.archivo.text.toString(),
                        fechaFormateada,
                        true,
                        viewModel.userId.value.toString()
                    )
                )
                val fragment = CatalogoEncargos()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                if (binding.title.text.isNotEmpty() && binding.desc.text.isNotEmpty() && !binding.archivo.text.contains("drive.google.com")) {
                    Toast.makeText(requireContext(), "El enlace no es de Google Drive", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}