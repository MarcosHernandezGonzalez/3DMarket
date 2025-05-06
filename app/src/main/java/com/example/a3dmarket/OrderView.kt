package com.example.a3dmarket

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.a3dmarket.databinding.FragmentOrderViewBinding

class OrderView : Fragment() {
    private lateinit var binding: FragmentOrderViewBinding
    private val viewModel: CatalogoVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderViewBinding.inflate(inflater, container, false)
        val orderId = arguments?.getString("id")
        orderId?.let { viewModel.getOrder(it) }
        viewModel.order.observe(viewLifecycleOwner) { order ->
            binding.textView10.text = order?.title
            binding.textView11.text = order?.desc
            order?.usuario?.let { viewModel.getUserInfo(it) }
            binding.file.setOnClickListener {
                val archivoUrl = order?.archivo
                if (archivoUrl?.let { it1 -> isGoogleDriveLink(it1) } == true) {
                    openDriveLink(requireContext(), archivoUrl)
                } else {
                    Toast.makeText(requireContext(), "El enlace no es de Google Drive", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.userInfo.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.textView12.text = user.name
                binding.textView12.setOnClickListener {
                    val fragment = Perfil()

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
        return binding.root
    }
    fun openDriveLink(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
    fun isGoogleDriveLink(url: String): Boolean {
        return url.contains("drive.google.com")
    }


}