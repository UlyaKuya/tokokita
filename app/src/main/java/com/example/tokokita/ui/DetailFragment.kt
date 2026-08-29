package com.example.tokokita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tokokita.databinding.FragmentDetailBinding
import com.example.tokokita.repository.ProdukRepository

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // Menerima productId dari Safe Args
    private val args: DetailFragmentArgs by navArgs()

    private val repository = ProdukRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        tampilkanDetailProduk()
    }

    private fun tampilkanDetailProduk() {

        val productId = args.productId

        val produk = repository.getProduk()
            .find { it.id == productId }

        if (produk != null) {

            binding.tvNama.text = produk.nama

            binding.tvHarga.text =
                "Harga: Rp ${produk.harga}"

            binding.tvKategori.text =
                "Kategori: ${produk.kategori}"

            binding.tvStok.text =
                "Stok: ${produk.stok}"

        } else {

            binding.tvNama.text = "Produk tidak ditemukan"
            binding.tvHarga.text = ""
            binding.tvKategori.text = ""
            binding.tvStok.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}